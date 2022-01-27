package com.company.qldp.peopleservice.web;

import com.company.qldp.domain.Stay;
import com.company.qldp.peopleservice.domain.assembler.StayRepresentationModelAssembler;
import com.company.qldp.peopleservice.domain.dto.StayDto;
import com.company.qldp.peopleservice.domain.service.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
    path = "/people/stays",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class StayController {
    
    private StayService stayService;
    
    private StayRepresentationModelAssembler assembler;
    
    @Autowired
    public StayController(
        StayService stayService,
        StayRepresentationModelAssembler assembler
    ) {
        this.stayService = stayService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Stay>>> createStay(
        @Valid StayDto stayDto,
        ServerWebExchange exchange
    ) {
        Stay stay = stayService.createStay(stayDto);
        
        return assembler.toModel(stay, exchange)
            .map(stayModel -> ResponseEntity
                .created(stayModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(stayModel)
            );
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Stay>> getStayById(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Stay stay = stayService.getStay(id);
        
        return assembler.toModel(stay, exchange);
    }
    
    @GetMapping
    public Mono<CollectionModel<EntityModel<Stay>>> getAllStays(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        
        List<Stay> stays = stayService.getStaysByFilters(queryParams);
        
        return assembler.toCollectionModel(Flux.fromIterable(stays), exchange);
    }
}
