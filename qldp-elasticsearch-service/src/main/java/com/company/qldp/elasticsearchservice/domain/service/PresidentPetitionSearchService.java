package com.company.qldp.elasticsearchservice.domain.service;

import com.company.qldp.common.Status;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.repository.PetitionSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PresidentPetitionSearchService {
    
    private PetitionSearchRepository petitionSearchRepository;
    
    @Autowired
    public PresidentPetitionSearchService(PetitionSearchRepository petitionSearchRepository) {
        this.petitionSearchRepository = petitionSearchRepository;
    }
    
    public Flux<PetitionSearch> getPetitions() {
        return petitionSearchRepository.findAllByStatus(Status.WAIT_FOR_REPLY);
    }
}
