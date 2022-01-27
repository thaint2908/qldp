package com.company.qldp.requestmanagementservice.web;

import com.company.qldp.domain.Petition;
import com.company.qldp.requestmanagementservice.domain.assembler.PetitionRepresentationModelAssembler;
import com.company.qldp.requestmanagementservice.domain.dto.PetitionDto;
import com.company.qldp.requestmanagementservice.domain.service.PetitionService;
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
import java.security.Principal;

@RestController
@RequestMapping(
    path = "/petitions",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class PetitionController {
    
    private PetitionService petitionService;
    
    private PetitionRepresentationModelAssembler assembler;
    
    @Autowired
    public PetitionController(
        PetitionService petitionService,
        PetitionRepresentationModelAssembler assembler
    ) {
        this.petitionService = petitionService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Petition>>> createPetition(
        @Valid PetitionDto petitionDto,
        ServerWebExchange exchange
    ) {
        return exchange.getPrincipal().flatMap(principal -> {
            Petition petition = petitionService.createPetition(principal.getName(), petitionDto);
    
            return assembler.toModel(petition, exchange)
                .map(petitionModel -> ResponseEntity
                    .created(petitionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(petitionModel)
                );
        });
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Petition>> getPetition(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Petition petition = petitionService.getPetitionById(id);
        
        return assembler.toModel(petition, exchange);
    }
    
    @DeleteMapping(path = "/{id}/reject")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Petition>> rejectPetition(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Petition petition = petitionService.rejectPetition(id);
        
        return assembler.toModel(petition, exchange);
    }
    
    @PatchMapping(path = "/{id}/accept")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Petition>> acceptPetition(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Petition petition = petitionService.acceptPetition(id);
        
        return assembler.toModel(petition, exchange);
    }
}
