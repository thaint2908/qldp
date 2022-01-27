package com.company.qldp.elasticsearchservice.domain.service;

import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.domain.repository.ReplySearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

@Service
public class ReplySearchService {
    
    private ReplySearchRepository replySearchRepository;
    
    @Autowired
    public ReplySearchService(ReplySearchRepository replySearchRepository) {
        this.replySearchRepository = replySearchRepository;
    }
    
    public Flux<ReplySearch> getRepliesByFilters(MultiValueMap<String, String> queryParams) {
        return replySearchRepository.findRepliesByFilters(queryParams);
    }
}
