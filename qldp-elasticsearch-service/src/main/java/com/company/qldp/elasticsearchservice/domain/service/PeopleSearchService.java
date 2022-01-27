package com.company.qldp.elasticsearchservice.domain.service;

import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import com.company.qldp.elasticsearchservice.domain.repository.PeopleSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

@Service
public class PeopleSearchService {
    
    private PeopleSearchRepository peopleSearchRepository;
    
    @Autowired
    public PeopleSearchService(PeopleSearchRepository peopleSearchRepository) {
        this.peopleSearchRepository = peopleSearchRepository;
    }
    
    public Flux<PeopleSearch> findPeopleByFilters(MultiValueMap<String, String> queryParams) {
        return peopleSearchRepository.findAllByFilters(queryParams);
    }
}
