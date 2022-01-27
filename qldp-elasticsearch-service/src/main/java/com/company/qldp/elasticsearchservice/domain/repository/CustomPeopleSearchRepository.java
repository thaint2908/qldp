package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

public interface CustomPeopleSearchRepository {
    
    Flux<PeopleSearch> findAllByFilters(MultiValueMap<String, String> queryParams);
}
