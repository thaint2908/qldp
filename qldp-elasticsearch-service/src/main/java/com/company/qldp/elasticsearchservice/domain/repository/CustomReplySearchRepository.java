package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

public interface CustomReplySearchRepository {
    
    Flux<ReplySearch> findRepliesByFilters(MultiValueMap<String, String> queryParams);
}
