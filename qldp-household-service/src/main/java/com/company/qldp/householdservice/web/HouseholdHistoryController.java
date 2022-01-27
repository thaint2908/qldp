package com.company.qldp.householdservice.web;

import com.company.qldp.domain.HouseholdHistory;
import com.company.qldp.householdservice.domain.assembler.HouseholdHistoryRepresentationModelAssembler;
import com.company.qldp.householdservice.domain.service.HouseholdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(
    path = "/households",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class HouseholdHistoryController {
    
    private HouseholdHistoryService householdHistoryService;
    
    private HouseholdHistoryRepresentationModelAssembler assembler;
    
    @Autowired
    public HouseholdHistoryController(
        HouseholdHistoryService householdHistoryService,
        HouseholdHistoryRepresentationModelAssembler assembler
    ) {
        this.householdHistoryService = householdHistoryService;
        this.assembler = assembler;
    }
    
    @GetMapping(path = "/{id}/histories")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<HouseholdHistory>>> getHouseholdHistories(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        List<HouseholdHistory> householdHistories = householdHistoryService.getHouseholdHistories(id);
        
        return assembler.toCollectionModel(
            Flux.fromIterable(householdHistories)
                .sort(Comparator.comparing(HouseholdHistory::getDate).reversed()),
            exchange
        );
    }
    
    @GetMapping(path = "/{id}/histories/{historyId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<HouseholdHistory>> getHouseholdHistory(
        @PathVariable("id") Integer id,
        @PathVariable("historyId") Integer historyId,
        ServerWebExchange exchange
    ) {
        HouseholdHistory householdHistory = householdHistoryService.getHouseholdHistory(id, historyId);
        
        return assembler.toModel(householdHistory, exchange);
    }
}
