package com.company.qldp.elasticsearchservice.domain.service;

import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.repository.PetitionSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

@Service
public class PetitionSearchService {
    
    private PetitionSearchRepository petitionSearchRepository;
    
    @Autowired
    public PetitionSearchService(PetitionSearchRepository petitionSearchRepository) {
        this.petitionSearchRepository = petitionSearchRepository;
    }
    
    public Flux<PetitionSearch> getPetitionsByFilters(MultiValueMap<String, String> queryParams) {
        return petitionSearchRepository.findPetitionsByFilters(queryParams);
    }
}
