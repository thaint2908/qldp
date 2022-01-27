package com.company.qldp.elasticsearchservice.web;

import com.company.qldp.elasticsearchservice.domain.assembler.HouseholdSearchRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
import com.company.qldp.elasticsearchservice.domain.service.HouseholdSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/households",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class HouseholdSearchController {
    
    private HouseholdSearchService householdSearchService;
    
    private HouseholdSearchRepresentationModelAssembler assembler;
    
    @Autowired
    public HouseholdSearchController(
        HouseholdSearchService householdSearchService,
        HouseholdSearchRepresentationModelAssembler assembler
    ) {
        this.householdSearchService = householdSearchService;
        this.assembler = assembler;
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<HouseholdSearch>>> getHouseholds(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        
        Flux<HouseholdSearch> householdSearchFlux = householdSearchService.getHouseholdsByFilters(queryParams);
        
        return assembler.toCollectionModel(householdSearchFlux, exchange);
    }
}
