package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.Family;
import com.company.qldp.peopleservice.domain.assembler.FamilyRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.FamilyDto;
import com.company.qldp.peopleservice.domain.service.FamilyService;
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
    path = "/people",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class FamilyController {
    
    private FamilyService familyService;
    
    private FamilyRepresentationModelAssembler assembler;
    
    @Autowired
    public FamilyController(
        FamilyService familyService,
        FamilyRepresentationModelAssembler assembler
    ) {
        this.familyService = familyService;
        this.assembler = assembler;
    }
    
    @PostMapping(
        path = "/{id}/family",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<CollectionModel<EntityModel<Family>>>> addFamily(
        @PathVariable("id") Integer id,
        @Valid @RequestBody FamilyDto familyDto,
        ServerWebExchange exchange
    ) {
        List<Family> familyList = familyService.addFamilyRelationToPeople(id, familyDto);
        
        return assembler.toCollectionModel(Flux.fromIterable(familyList), exchange)
            .map(familyModel -> ResponseEntity
                .created(familyModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(familyModel)
            );
    }
    
    @GetMapping(path = "/{id}/family/{familyId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Family>> getFamily(
        @PathVariable("id") Integer id,
        @PathVariable("familyId") Integer familyId,
        ServerWebExchange exchange
    ) {
        Family family = familyService.getFamilyByPeopleId(id, familyId);
        
        return assembler.toModel(family, exchange);
    }
    
    @GetMapping(path = "/{id}/family")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<Family>>> getFamilies(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        List<Family> familyList = familyService.getFamiliesByPeopleId(id);
        
        return assembler.toCollectionModel(Flux.fromIterable(familyList), exchange);
    }
}
