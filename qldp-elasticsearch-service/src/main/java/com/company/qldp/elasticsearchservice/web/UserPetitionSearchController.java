package com.company.qldp.elasticsearchservice.web;

import com.company.qldp.elasticsearchservice.domain.assembler.UserPetitionSearchRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.domain.service.UserPetitionSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
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
    path = "/users/petitions",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class UserPetitionSearchController {
    
    private UserPetitionSearchService userPetitionSearchService;
    
    private UserPetitionSearchRepresentationModelAssembler assembler;
    
    @Autowired
    public UserPetitionSearchController(
        UserPetitionSearchService userPetitionSearchService,
        UserPetitionSearchRepresentationModelAssembler assembler
    ) {
        this.userPetitionSearchService = userPetitionSearchService;
        this.assembler = assembler;
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<PetitionSearch>>> getPetitions(ServerWebExchange exchange) {
        return exchange.getPrincipal().flatMap(principal -> {
            String uid = principal.getName();
    
            Flux<PetitionSearch> petitionSearchFlux = userPetitionSearchService.getPetitionsByUser(uid)
                .sort(Comparator.comparing(PetitionSearch::getDate).reversed());
            
            return assembler.toCollectionModel(petitionSearchFlux, exchange);
        });
    }
}
