package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.IDCard;
import com.company.qldp.peopleservice.domain.assembler.IDCardRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.IDCardDto;
import com.company.qldp.peopleservice.domain.service.IDCardService;
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
    path = "/people",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class IDCardController {
    
    private IDCardService idCardService;
    
    private IDCardRepresentationModelAssembler assembler;
    
    @Autowired
    public IDCardController(
        IDCardService idCardService,
        IDCardRepresentationModelAssembler assembler
    ) {
        this.idCardService = idCardService;
        this.assembler = assembler;
    }
    
    @PostMapping(
        path = "/{id}/id-card",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<EntityModel<IDCard>>> createIDCard(
        @PathVariable("id") Integer id,
        @Valid IDCardDto idCardDto,
        ServerWebExchange exchange
    ) {
        IDCard idCard = idCardService.createPeopleIDCard(id, idCardDto);
        
        return assembler.toModel(idCard, exchange)
            .map(idCardModel -> ResponseEntity
                .created(idCardModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(idCardModel)
            );
    }
    
    @GetMapping(path = "/{id}/id-card")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<IDCard>> getIDCardByPeopleId(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        IDCard idCard = idCardService.getIDCardByPeopleId(id);
        
        return assembler.toModel(idCard, exchange);
    }
}
