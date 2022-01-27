package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.common.Status;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface PetitionSearchRepository
    extends ReactiveElasticsearchRepository<PetitionSearch, Integer>, CustomPetitionSearchRepository {
    
    Flux<PetitionSearch> findAllBySender(String sender);
    
    Flux<PetitionSearch> findAllByStatus(Status status);
}
