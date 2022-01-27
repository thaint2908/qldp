package com.company.qldp.elasticsearchservice.domain.repository;

import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
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
public class CustomHouseholdSearchRepositoryImpl implements CustomHouseholdSearchRepository {
    
    private ReactiveElasticsearchOperations operations;
    
    @Autowired
    public CustomHouseholdSearchRepositoryImpl(ReactiveElasticsearchOperations operations) {
        this.operations = operations;
    }
    
    @Override
    public Flux<HouseholdSearch> findHouseholdsByFilters(MultiValueMap<String, String> queryParams) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        
        String hostName = queryParams.getFirst("host");
        String address = queryParams.getFirst("address");
    
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        
        if (hostName != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(hostName, "host.full_name", "host.full_name.search", "host.full_name.search._2gram", "host.full_name.search._3gram")
                    .type(Type.BOOL_PREFIX)
            );
        }
        if (address != null) {
            boolQueryBuilder = boolQueryBuilder.must(
                multiMatchQuery(address, "address.search", "address.search._2gram", "address.search._3gram")
                    .type(Type.BOOL_PREFIX)
            );
        }
        
        Flux<HouseholdSearch> householdSearchFlux = operations.search(queryBuilder.withQuery(boolQueryBuilder).build(), HouseholdSearch.class)
            .map(SearchHit::getContent);
        
        return householdSearchFlux;
    }
}
