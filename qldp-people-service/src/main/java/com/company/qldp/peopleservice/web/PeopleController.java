package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.People;
import com.company.qldp.peopleservice.domain.assembler.PeopleRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.LeavePeopleDto;
import com.company.qldp.peopleservice.domain.dto.PersonDto;
import com.company.qldp.peopleservice.domain.dto.UpdatePersonDto;
import com.company.qldp.peopleservice.domain.service.PeopleService;
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
public class PeopleController {
    
    private PeopleService peopleService;
    
    private PeopleRepresentationModelAssembler assembler;
    
    @Autowired
    public PeopleController(
        PeopleService peopleService,
        PeopleRepresentationModelAssembler assembler
    ) {
        this.peopleService = peopleService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<People>>> createPerson(
        @Valid PersonDto personDto,
        ServerWebExchange exchange
    ) {
        People person = peopleService.createPeople(personDto);
        
        return assembler.toModel(person, exchange)
            .map(peopleModel -> ResponseEntity
                .created(peopleModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(peopleModel)
            );
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<People>> getPersonById(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        People person = peopleService.findPersonById(id);
        
        return assembler.toModel(person, exchange);
    }
    
    @PatchMapping(
        path = "/{id}/leave",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<People>> leavePerson(
        @PathVariable("id") Integer id,
        @Valid LeavePeopleDto leavePeopleDto,
        ServerWebExchange exchange
    ) {
        People people = peopleService.leavePeople(id, leavePeopleDto);
        
        return assembler.toModel(people, exchange);
    }
    
    @PatchMapping(
        path = "/{id}",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<People>> updatePersonInfo(
        @PathVariable("id") Integer id,
        UpdatePersonDto updatePersonDto,
        ServerWebExchange exchange
    ) {
        People updatedPerson = peopleService.updatePeopleInfo(id, updatePersonDto);
        
        return assembler.toModel(updatedPerson, exchange);
    }
}
