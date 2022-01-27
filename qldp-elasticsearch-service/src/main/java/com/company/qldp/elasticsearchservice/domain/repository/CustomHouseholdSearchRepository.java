package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

public interface CustomHouseholdSearchRepository {
    
    Flux<HouseholdSearch> findHouseholdsByFilters(MultiValueMap<String, String> queryParams);
}
