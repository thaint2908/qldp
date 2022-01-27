package com.company.qldp.householdservice.web;

import com.company.qldp.domain.FamilyMember;
import com.company.qldp.householdservice.domain.assembler.FamilyMemberRepresentationModelAssembler;
import com.company.qldp.householdservice.domain.dto.FamilyMemberDto;
import com.company.qldp.householdservice.domain.service.FamilyMemberService;
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
public class FamilyMemberController {
    
    private FamilyMemberService familyMemberService;
    
    private FamilyMemberRepresentationModelAssembler assembler;
    
    @Autowired
    public FamilyMemberController(
        FamilyMemberService familyMemberService,
        FamilyMemberRepresentationModelAssembler assembler
    ) {
        this.familyMemberService = familyMemberService;
        this.assembler = assembler;
    }
    
    @PostMapping(
        path = "/{id}/members",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<CollectionModel<EntityModel<FamilyMember>>>> addPeople(
        @PathVariable("id") Integer id,
        @Valid @RequestBody FamilyMemberDto familyMemberDto,
        ServerWebExchange exchange
    ) {
        List<FamilyMember> familyMembers = familyMemberService.addFamilyMembers(id, familyMemberDto);
        
        return assembler.toCollectionModel(Flux.fromIterable(familyMembers), exchange)
            .map(memberCollectionModel -> ResponseEntity
                .created(memberCollectionModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(memberCollectionModel)
            );
    }
    
    @GetMapping(path = "/{id}/members/{memberId}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<FamilyMember>> getMember(
        @PathVariable("id") Integer id,
        @PathVariable("memberId") Integer memberId,
        ServerWebExchange exchange
    ) {
        FamilyMember member = familyMemberService.getFamilyMember(id, memberId);
        
        return assembler.toModel(member, exchange);
    }
    
    @GetMapping(path = "/{id}/members")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<FamilyMember>>> getAllMembers(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        List<FamilyMember> familyMembers = familyMemberService.getFamilyMembers(id);
        
        return assembler.toCollectionModel(Flux.fromIterable(familyMembers), exchange);
    }
}
