package com.company.qldp.elasticsearchservice.domain.service;

import com.company.qldp.domain.User;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.repository.PetitionSearchRepository;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserPetitionSearchService {
    
    private PetitionSearchRepository petitionSearchRepository;
    private UserRepository userRepository;
    
    @Autowired
    public UserPetitionSearchService(
        PetitionSearchRepository petitionSearchRepository,
        UserRepository userRepository
    ) {
        this.petitionSearchRepository = petitionSearchRepository;
        this.userRepository = userRepository;
    }
    
    public Flux<PetitionSearch> getPetitionsByUser(String keycloakUid) {
        User user = userRepository.findByKeycloakUid(keycloakUid);
        
        if (user == null) {
            throw new UserNotFoundException();
        }
        
        return petitionSearchRepository.findAllBySender(user.getUsername());
    }
}
