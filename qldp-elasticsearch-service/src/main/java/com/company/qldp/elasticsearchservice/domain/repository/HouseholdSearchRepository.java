package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface HouseholdSearchRepository
    extends ReactiveElasticsearchRepository<HouseholdSearch, Integer>, CustomHouseholdSearchRepository {
    
}
