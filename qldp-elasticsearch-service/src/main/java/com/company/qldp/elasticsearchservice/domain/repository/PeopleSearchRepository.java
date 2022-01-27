package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface PeopleSearchRepository
    extends ReactiveElasticsearchRepository<PeopleSearch, Integer>, CustomPeopleSearchRepository {
    
}
