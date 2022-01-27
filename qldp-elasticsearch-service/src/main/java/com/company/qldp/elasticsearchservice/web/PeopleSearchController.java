package com.company.qldp.elasticsearchservice.web;

import com.company.qldp.elasticsearchservice.domain.assembler.PeopleSearchRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import com.company.qldp.elasticsearchservice.domain.service.PeopleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/people",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class PeopleSearchController {
    
    private PeopleSearchService peopleSearchService;
    
    private PeopleSearchRepresentationModelAssembler assembler;
    
    @Autowired
    public PeopleSearchController(
        PeopleSearchService peopleSearchService,
        PeopleSearchRepresentationModelAssembler assembler
    ) {
        this.peopleSearchService = peopleSearchService;
        this.assembler = assembler;
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<PeopleSearch>>> getPeople(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        
        Flux<PeopleSearch> peopleSearchFlux = peopleSearchService.findPeopleByFilters(queryParams);
        
        return assembler.toCollectionModel(peopleSearchFlux, exchange);
    }
}
