package com.company.qldp.householdservice.web;

import com.company.qldp.domain.Correction;
import com.company.qldp.householdservice.domain.assembler.CorrectionRepresentationModelAssembler;
import com.company.qldp.householdservice.domain.dto.CorrectionDto;
import com.company.qldp.householdservice.domain.service.CorrectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
    path = "/households",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class CorrectionController {
    
    private CorrectionService correctionService;
    
    private CorrectionRepresentationModelAssembler assembler;
    
    @Autowired
    public CorrectionController(
        CorrectionService correctionService,
        CorrectionRepresentationModelAssembler assembler
    ) {
        this.correctionService = correctionService;
        this.assembler = assembler;
    }
    
    @PostMapping(
        path = "/{id}/corrections",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Correction>>> createCorrection(
        @PathVariable("id") Integer id,
        @Valid CorrectionDto correctionDto,
        ServerWebExchange exchange
    ) {
        Correction correction = correctionService.createCorrection(id, correctionDto);
        
        return assembler.toModel(correction, exchange)
            .map(correctionModel -> ResponseEntity
                .created(correctionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(correctionModel)
            );
    }
    
    @GetMapping(path = "/{id}/corrections/{corrId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Correction>> getCorrection(
        @PathVariable("id") Integer id,
        @PathVariable("corrId") Integer corrId,
        ServerWebExchange exchange
    ) {
        Correction correction = correctionService.getCorrectionByHouseholdId(id, corrId);
        
        return assembler.toModel(correction, exchange);
    }
    
    @GetMapping(path = "/{id}/corrections")
    public Mono<CollectionModel<EntityModel<Correction>>> getAllCorrections(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        List<Correction> corrections = correctionService.getCorrectionsByHouseholdId(id);
        
        return assembler.toCollectionModel(Flux.fromIterable(corrections), exchange);
    }
}
