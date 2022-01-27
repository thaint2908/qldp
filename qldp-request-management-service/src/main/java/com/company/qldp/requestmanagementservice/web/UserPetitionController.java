package com.company.qldp.requestmanagementservice.web;

import com.company.qldp.domain.Petition;
import com.company.qldp.requestmanagementservice.domain.assembler.UserPetitionRepresentationModelAssembler;
import com.company.qldp.requestmanagementservice.domain.service.UserPetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/users",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class UserPetitionController {
    
    private UserPetitionService userPetitionService;
    
    private UserPetitionRepresentationModelAssembler assembler;
    
    @Autowired
    public UserPetitionController(
        UserPetitionService userPetitionService,
        UserPetitionRepresentationModelAssembler assembler
    ) {
        this.userPetitionService = userPetitionService;
        this.assembler = assembler;
    }
    
    @GetMapping(path = "/petitions/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Petition>> getPetition(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        return exchange.getPrincipal().flatMap(principal -> {
            String keycloakUid = principal.getName();
    
            Petition petition = userPetitionService.getPetition(keycloakUid, id);
    
            return assembler.toModel(petition, exchange);
        });
    }
}
