package com.company.qldp.requestmanagementservice.domain.service;

import com.company.qldp.common.ContentBody;
import com.company.qldp.common.Status;
import com.company.qldp.domain.Petition;
import com.company.qldp.domain.User;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.repository.PetitionSearchRepository;
import com.company.qldp.requestmanagementservice.domain.dto.PetitionDto;
import com.company.qldp.requestmanagementservice.domain.exception.InvalidPetitionUpdateStatusException;
import com.company.qldp.requestmanagementservice.domain.exception.PetitionNotFoundException;
import com.company.qldp.requestmanagementservice.domain.repository.PetitionRepository;
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
public class PetitionService {
    
    private PetitionRepository petitionRepository;
    private UserRepository userRepository;
    private PetitionSearchRepository petitionSearchRepository;
    
    @Autowired
    public PetitionService(
        PetitionRepository petitionRepository,
        UserRepository userRepository,
        PetitionSearchRepository petitionSearchRepository
    ) {
        this.petitionRepository = petitionRepository;
        this.userRepository = userRepository;
        this.petitionSearchRepository = petitionSearchRepository;
    }
    
    @Transactional
    public Petition createPetition(String keycloakUid, PetitionDto petitionDto) {
        User user = userRepository.findByKeycloakUid(keycloakUid);
        
        if (user == null) {
            throw new UserNotFoundException();
        }
    
        ContentBody body = ContentBody.builder()
            .subject(petitionDto.getSubject())
            .content(petitionDto.getContent())
            .date(Date.from(Instant.parse(petitionDto.getDate())))
            .status(Status.PENDING)
            .build();
        
        Petition petition = Petition.builder()
            .body(body)
            .sender(user)
            .build();
        Petition savedPetition = petitionRepository.save(petition);
        GetInfo.getPetitionInfo(savedPetition);
    
        PetitionSearch petitionSearch = PetitionSearch.builder()
            .id(savedPetition.getId())
            .subject(savedPetition.getBody().getSubject())
            .sender(savedPetition.getSender().getUsername())
            .date(savedPetition.getBody().getDate())
            .status(savedPetition.getBody().getStatus())
            .build();
        petitionSearchRepository.save(petitionSearch).subscribe();
        
        return savedPetition;
    }
    
    @Transactional
    public Petition getPetitionById(Integer id) {
        Petition petition = petitionRepository.findById(id)
            .orElseThrow(PetitionNotFoundException::new);
        GetInfo.getPetitionInfo(petition);
        
        return petition;
    }
    
    @Transactional
    public Petition rejectPetition(Integer id) {
        Petition petition = getPetitionById(id);
        
        if (petition.getBody().getStatus() == Status.PENDING) {
            ContentBody body = petition.getBody();
            body.setStatus(Status.REJECTED);
            petition.setBody(body);
            
            Petition savedPetition = petitionRepository.save(petition);
            savePetitionSearchStatus(savedPetition);
            
            GetInfo.getPetitionInfo(savedPetition);
            
            return savedPetition;
        } else {
            throw new InvalidPetitionUpdateStatusException(petition.getBody().getStatus().toString());
        }
    }
    
    @Transactional
    public Petition acceptPetition(Integer id) {
        Petition petition = getPetitionById(id);
        
        if (petition.getBody().getStatus() == Status.PENDING) {
            ContentBody body = petition.getBody();
            body.setStatus(Status.WAIT_FOR_REPLY);
            petition.setBody(body);
            
            Petition savedPetition = petitionRepository.save(petition);
            savePetitionSearchStatus(savedPetition);
            
            GetInfo.getPetitionInfo(savedPetition);
            
            return savedPetition;
        } else {
            throw new InvalidPetitionUpdateStatusException(petition.getBody().getStatus().toString());
        }
    }
    
    private void savePetitionSearchStatus(Petition petition) {
        petitionSearchRepository.findById(petition.getId()).map(petitionSearch -> {
            petitionSearch.setStatus(petition.getBody().getStatus());
        
            return petitionSearchRepository.save(petitionSearch);
        }).subscribe(Mono::subscribe);
    }
}
