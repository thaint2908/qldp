package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.MultiMatchQueryBuilder.*;

@Repository
public class CustomReplySearchRepositoryImpl implements CustomReplySearchRepository {
    
    private ReactiveElasticsearchOperations operations;
    
    @Autowired
    public CustomReplySearchRepositoryImpl(ReactiveElasticsearchOperations operations) {
        this.operations = operations;
    }
    
    @Override
    public Flux<ReplySearch> findRepliesByFilters(MultiValueMap<String, String> queryParams) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        
        String subject = queryParams.getFirst("subject");
        String replier = queryParams.getFirst("replier");
    
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        
        if (subject != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(subject, "subject.search", "subject.search._2gram", "subject.search,_3gram")
                    .type(Type.BOOL_PREFIX).minimumShouldMatch("100%")
            );
        }
        if (replier != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(replier, "replier.search", "replier.search._2gram", "replier.search._3gram")
                    .type(Type.BOOL_PREFIX).minimumShouldMatch("100%")
            );
        }
        
        Flux<ReplySearch> replySearchFlux = operations.search(queryBuilder.withQuery(boolQueryBuilder).build(), ReplySearch.class)
            .map(SearchHit::getContent);
        
        return replySearchFlux;
    }
}
