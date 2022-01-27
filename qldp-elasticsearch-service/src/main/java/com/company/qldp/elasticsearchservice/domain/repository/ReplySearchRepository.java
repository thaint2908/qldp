package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

public interface ReplySearchRepository
    extends ReactiveElasticsearchRepository<ReplySearch, Integer>, CustomReplySearchRepository {
    
    Flux<ReplySearch> findAllByPetition_Sender(String sender);
    
    Flux<ReplySearch> findAllByReplier(String replier);
}
