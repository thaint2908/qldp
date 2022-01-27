package com.company.qldp.elasticsearchservice.web;

import com.company.qldp.elasticsearchservice.domain.assembler.PetitionSearchRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.service.PetitionSearchService;
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

import java.util.Comparator;

@RestController
@RequestMapping(
    path = "/petitions",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class PetitionSearchController {
    
    private PetitionSearchService petitionSearchService;
    
    private PetitionSearchRepresentationModelAssembler assembler;
    
    @Autowired
    public PetitionSearchController(
        PetitionSearchService petitionSearchService,
        PetitionSearchRepresentationModelAssembler assembler
    ) {
        this.petitionSearchService = petitionSearchService;
        this.assembler = assembler;
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<PetitionSearch>>> getPetitions(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
    
        Flux<PetitionSearch> petitionSearchFlux = petitionSearchService.getPetitionsByFilters(queryParams)
            .sort(Comparator.comparing(PetitionSearch::getDate).reversed());
        
        return assembler.toCollectionModel(petitionSearchFlux, exchange);
    }
}
