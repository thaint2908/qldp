package com.company.qldp.requestmanagementservice.domain.service;

import com.company.qldp.common.Status;
import com.company.qldp.domain.Petition;
import com.company.qldp.requestmanagementservice.domain.repository.PetitionRepository;
import com.company.qldp.requestmanagementservice.domain.util.GetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PresidentPetitionService {
    
    private PetitionRepository petitionRepository;
    
    @Autowired
    public PresidentPetitionService(PetitionRepository petitionRepository) {
        this.petitionRepository = petitionRepository;
    }
    
    @Transactional
    public Petition getPetition(Integer id) {
        Petition petition = petitionRepository.findByBody_StatusAndId(Status.WAIT_FOR_REPLY, id);
        GetInfo.getPetitionInfo(petition);
        
        return petition;
    }
}
