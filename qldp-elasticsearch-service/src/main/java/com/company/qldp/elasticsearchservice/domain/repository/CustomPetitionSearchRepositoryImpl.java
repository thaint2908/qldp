package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
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
public class CustomPetitionSearchRepositoryImpl implements CustomPetitionSearchRepository {
    
    private ReactiveElasticsearchOperations operations;
    
    @Autowired
    public CustomPetitionSearchRepositoryImpl(ReactiveElasticsearchOperations operations) {
        this.operations = operations;
    }
    
    @Override
    public Flux<PetitionSearch> findPetitionsByFilters(MultiValueMap<String, String> queryParams) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        
        String subject = queryParams.getFirst("subject");
        String sender = queryParams.getFirst("sender");
    
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        
        if (subject != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(subject, "subject.search", "subject.search._2gram", "subject.search._3gram")
                    .type(Type.BOOL_PREFIX).minimumShouldMatch("100%")
            );
        }
        if (sender != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(sender, "sender.search", "sender.search._2gram", "sender.search._3gram")
                    .type(Type.BOOL_PREFIX).minimumShouldMatch("100%")
            );
        }
        
        Flux<PetitionSearch> petitionSearchFlux = operations.search(queryBuilder.withQuery(boolQueryBuilder).build(), PetitionSearch.class)
            .map(SearchHit::getContent);
        
        return petitionSearchFlux;
    }
}
