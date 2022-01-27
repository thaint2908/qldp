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
public class UserReplySearchService {
    
    private ReplySearchRepository replySearchRepository;
    private UserRepository userRepository;
    
    @Autowired
    public UserReplySearchService(
        ReplySearchRepository replySearchRepository,
        UserRepository userRepository
    ) {
        this.replySearchRepository = replySearchRepository;
        this.userRepository = userRepository;
    }
    
    public Flux<ReplySearch> getReplySearchByUser(String keycloakUid) {
        User sender = userRepository.findByKeycloakUid(keycloakUid);
        
        if (sender == null) {
            throw new UserNotFoundException();
        }
        
        return replySearchRepository.findAllByPetition_Sender(sender.getUsername());
    }
}
