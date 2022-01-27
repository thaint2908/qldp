package com.company.qldp.requestmanagementservice.web;

import com.company.qldp.domain.Petition;
import com.company.qldp.requestmanagementservice.domain.assembler.PresidentPetitionRepresentationModelAssembler;
import com.company.qldp.requestmanagementservice.domain.service.PresidentPetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/presidents/petitions",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class PresidentPetitionController {
    
    private PresidentPetitionService presidentPetitionService;
    
    private PresidentPetitionRepresentationModelAssembler assembler;
    
    @Autowired
    public PresidentPetitionController(
        PresidentPetitionService presidentPetitionService,
        PresidentPetitionRepresentationModelAssembler assembler
    ) {
        this.presidentPetitionService = presidentPetitionService;
        this.assembler = assembler;
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Petition>> getPetition(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Petition petition = presidentPetitionService.getPetition(id);
        
        return assembler.toModel(petition, exchange);
    }
}
