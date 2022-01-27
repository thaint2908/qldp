package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

public interface CustomPetitionSearchRepository {
    
    Flux<PetitionSearch> findPetitionsByFilters(MultiValueMap<String, String> queryParams);
}
