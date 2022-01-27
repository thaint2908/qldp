package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.Death;
import com.company.qldp.peopleservice.domain.assembler.DeathRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.DeathDto;
import com.company.qldp.peopleservice.domain.service.DeathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
    path = "/people/death",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class DeathController {
    
    private DeathService deathService;
    
    private DeathRepresentationModelAssembler assembler;
    
    @Autowired
    public DeathController(
        DeathService deathService,
        DeathRepresentationModelAssembler assembler
    ) {
        this.deathService = deathService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Death>>> createDeath(
        @Valid DeathDto deathDto,
        ServerWebExchange exchange
    ) {
        Death death = deathService.createDeath(deathDto);
        
        return assembler.toModel(death, exchange)
            .map(deathModel -> ResponseEntity
                .created(deathModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(deathModel)
            );
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Death>> getDeathById(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Death death = deathService.getDeath(id);
        
        return assembler.toModel(death, exchange);
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<Death>>> getAllDeaths(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        
        List<Death> deaths = deathService.getDeathsByFilters(queryParams);
        
        return assembler.toCollectionModel(Flux.fromIterable(deaths), exchange);
    }
}
