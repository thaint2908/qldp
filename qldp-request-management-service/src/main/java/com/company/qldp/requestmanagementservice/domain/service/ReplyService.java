package com.company.qldp.requestmanagementservice.domain.service;

import com.company.qldp.common.ContentBody;
import com.company.qldp.common.Status;
import com.company.qldp.domain.Petition;
import com.company.qldp.domain.Reply;
import com.company.qldp.domain.User;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.domain.repository.PetitionSearchRepository;
import com.company.qldp.elasticsearchservice.domain.repository.ReplySearchRepository;
import com.company.qldp.requestmanagementservice.domain.dto.ReplyDto;
import com.company.qldp.requestmanagementservice.domain.exception.InvalidPetitionStatusException;
import com.company.qldp.requestmanagementservice.domain.exception.InvalidReplyUpdateStatusException;
import com.company.qldp.requestmanagementservice.domain.exception.PetitionNotFoundException;
import com.company.qldp.requestmanagementservice.domain.exception.ReplyNotFoundException;
import com.company.qldp.requestmanagementservice.domain.repository.PetitionRepository;
import com.company.qldp.requestmanagementservice.domain.repository.ReplyRepository;
import com.company.qldp.requestmanagementservice.domain.util.GetInfo;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;

@Service
public class ReplyService {
    
    private ReplyRepository replyRepository;
    private UserRepository userRepository;
    private PetitionRepository petitionRepository;
    private ReplySearchRepository replySearchRepository;
    private PetitionSearchRepository petitionSearchRepository;
    
    @Autowired
    public ReplyService(
        ReplyRepository replyRepository,
        UserRepository userRepository,
        PetitionRepository petitionRepository,
        ReplySearchRepository replySearchRepository,
        PetitionSearchRepository petitionSearchRepository
    ) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.petitionRepository = petitionRepository;
        this.replySearchRepository = replySearchRepository;
        this.petitionSearchRepository = petitionSearchRepository;
    }
    
    @Transactional
    public Reply createReply(String keycloakUid, ReplyDto replyDto) {
        User replier = userRepository.findByKeycloakUid(keycloakUid);
        Petition petition = petitionRepository.findById(replyDto.getToPetition())
            .orElseThrow(PetitionNotFoundException::new);
        
        if (replier == null) {
            throw new UserNotFoundException();
        }
        if (petition.getBody().getStatus() != Status.WAIT_FOR_REPLY) {
            throw new InvalidPetitionStatusException();
        }
    
        ContentBody body = ContentBody.builder()
            .subject(replyDto.getSubject())
            .content(replyDto.getContent())
            .status(Status.PENDING)
            .date(Date.from(Instant.parse(replyDto.getDate())))
            .build();
        Reply reply = Reply.builder()
            .body(body)
            .petition(petition)
            .replier(replier)
            .build();
        Reply savedReply = replyRepository.save(reply);
        GetInfo.getReplyInfo(savedReply);
        
        petitionSearchRepository.findById(replyDto.getToPetition()).map(petitionSearch -> {
            ReplySearch replySearch = ReplySearch.builder()
                .id(savedReply.getId())
                .subject(savedReply.getBody().getSubject())
                .status(savedReply.getBody().getStatus())
                .date(savedReply.getBody().getDate())
                .petition(petitionSearch)
                .replier(savedReply.getReplier().getUsername())
                .build();
    
            return replySearchRepository.save(replySearch);
        }).subscribe(Mono::subscribe);
        
        return savedReply;
    }
    
    @Transactional
    public Reply getReply(Integer id) {
        Reply reply = replyRepository.findById(id)
            .orElseThrow(ReplyNotFoundException::new);
        GetInfo.getReplyInfo(reply);
        
        return reply;
    }
    
    @Transactional
    public Reply acceptReply(Integer id) {
        Reply reply = getReply(id);
        
        if (reply.getBody().getStatus() == Status.PENDING) {
            ContentBody replyBody = reply.getBody();
            replyBody.setStatus(Status.SENT_TO_USER);
            reply.setBody(replyBody);
            
            Petition petition = reply.getPetition();
            ContentBody petitionBody = petition.getBody();
            petitionBody.setStatus(Status.REPLIED);
            petition.setBody(petitionBody);
            
            Reply savedReply = replyRepository.save(reply);
            saveReplySearchStatus(savedReply);
            
            GetInfo.getReplyInfo(savedReply);
            
            return savedReply;
        } else {
            throw new InvalidReplyUpdateStatusException(reply.getBody().getStatus().toString());
        }
    }
    
    private void saveReplySearchStatus(Reply reply) {
        replySearchRepository.findById(reply.getId()).map(replySearch -> {
            PetitionSearch petitionSearch = replySearch.getPetition();
            petitionSearch.setStatus(Status.REPLIED);
            
            replySearch.setPetition(petitionSearch);
            replySearch.setStatus(Status.SENT_TO_USER);
        
            return replySearchRepository.save(replySearch);
        }).subscribe(Mono::subscribe);
    }
}
