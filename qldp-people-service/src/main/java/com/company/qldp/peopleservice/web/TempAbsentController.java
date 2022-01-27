package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.TempAbsent;
import com.company.qldp.peopleservice.domain.assembler.TempAbsentRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.TempAbsentDto;
import com.company.qldp.peopleservice.domain.service.TempAbsentService;
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
    path = "/people/temp-absents",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class TempAbsentController {
    
    private TempAbsentService tempAbsentService;
    
    private TempAbsentRepresentationModelAssembler assembler;
    
    @Autowired
    public TempAbsentController(
        TempAbsentService tempAbsentService,
        TempAbsentRepresentationModelAssembler assembler
    ) {
        this.tempAbsentService = tempAbsentService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<TempAbsent>>> createTempAbsent(
        @Valid TempAbsentDto tempAbsentDto,
        ServerWebExchange exchange
    ) {
        TempAbsent tempAbsent = tempAbsentService.createTempAbsent(tempAbsentDto);
        
        return assembler.toModel(tempAbsent, exchange)
            .map(tempAbsentModel -> ResponseEntity
                .created(tempAbsentModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(tempAbsentModel)
            );
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<TempAbsent>> getTempAbsentById(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        TempAbsent tempAbsent = tempAbsentService.getTempAbsent(id);
        
        return assembler.toModel(tempAbsent, exchange);
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<TempAbsent>>> getAllTempAbsents(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        
        List<TempAbsent> tempAbsents = tempAbsentService.getTempAbsentsByFilters(queryParams);
        
        return assembler.toCollectionModel(Flux.fromIterable(tempAbsents), exchange);
    }
}
