package com.company.qldp.requestmanagementservice.domain.service;

import com.company.qldp.domain.Petition;
import com.company.qldp.requestmanagementservice.domain.repository.PetitionRepository;
import com.company.qldp.requestmanagementservice.domain.util.GetInfo;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPetitionService {
    
    private PetitionRepository petitionRepository;
    private UserRepository userRepository;
    
    @Autowired
    public UserPetitionService(
        PetitionRepository petitionRepository,
        UserRepository userRepository
    ) {
        this.petitionRepository = petitionRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public Petition getPetition(String keycloakUid, Integer petitionId) {
        if (userNotExists(keycloakUid)) {
            throw new UserNotFoundException();
        }
        
        Petition petition = petitionRepository.findBySender_KeycloakUidAndId(keycloakUid, petitionId);
        GetInfo.getPetitionInfo(petition);
        
        return petition;
    }
    
    private boolean userNotExists(String uid) {
        return userRepository.findByKeycloakUid(uid) == null;
    }
}
