package com.company.qldp.elasticsearchservice.domain.service;

import com.company.qldp.domain.User;
import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.domain.repository.ReplySearchRepository;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PresidentReplySearchService {
    
    private ReplySearchRepository replySearchRepository;
    private UserRepository userRepository;
    
    @Autowired
    public PresidentReplySearchService(
        ReplySearchRepository replySearchRepository,
        UserRepository userRepository
    ) {
        this.replySearchRepository = replySearchRepository;
        this.userRepository = userRepository;
    }
    
    public Flux<ReplySearch> getRepliesByUser(String keycloakUid) {
        User replier = userRepository.findByKeycloakUid(keycloakUid);
        
        if (replier == null) {
            throw new UserNotFoundException();
        }
        
        return replySearchRepository.findAllByReplier(replier.getUsername());
    }
}
