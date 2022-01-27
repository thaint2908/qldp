package com.company.qldp.householdservice.web;

import com.company.qldp.domain.Household;
import com.company.qldp.householdservice.domain.assembler.HouseholdRepresentationModelAssembler;
import com.company.qldp.householdservice.domain.dto.HouseholdDto;
import com.company.qldp.householdservice.domain.dto.LeaveHouseholdDto;
import com.company.qldp.householdservice.domain.dto.SeparateHouseholdDto;
import com.company.qldp.householdservice.domain.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(
    path = "/households",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class HouseholdController {
    
    private HouseholdService householdService;
    
    private HouseholdRepresentationModelAssembler assembler;
    
    @Autowired
    public HouseholdController(
        HouseholdService householdService,
        HouseholdRepresentationModelAssembler assembler
    ) {
        this.householdService = householdService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Household>>> createHousehold(
        @Valid HouseholdDto householdDto,
        ServerWebExchange exchange
    ) {
        Household household = householdService.createHousehold(householdDto);
        
        return assembler.toModel(household, exchange)
            .map(householdModel -> ResponseEntity
                .created(householdModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(householdModel)
            );
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Household>> getHouseholdById(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Household household = householdService.getHousehold(id);
        
        return assembler.toModel(household, exchange);
    }
    
    @PatchMapping(
        path = "/{id}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Household>> leaveHousehold(
        @PathVariable("id") Integer id,
        @Valid LeaveHouseholdDto leaveHouseholdDto,
        ServerWebExchange exchange
    ) {
        Household household = householdService.leaveHousehold(id, leaveHouseholdDto);
        
        return assembler.toModel(household, exchange);
    }
    
    @PatchMapping(
        path = "/{id}/separate",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<Household>>> separateHousehold(
        @PathVariable("id") Integer id,
        @Valid SeparateHouseholdDto separateHouseholdDto,
        ServerWebExchange exchange
    ) {
        Household newHousehold = householdService.separateHousehold(id, separateHouseholdDto);
        
        return assembler.toModel(newHousehold, exchange)
            .map(householdModel -> ResponseEntity
                .created(householdModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(householdModel)
            );
    }
}
