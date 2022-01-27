package com.company.qldp.elasticsearchservice.web;

import com.company.qldp.elasticsearchservice.domain.assembler.PresidentReplySearchRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.domain.service.PresidentReplySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
@RequestMapping(
    path = "/presidents/replies",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class PresidentReplySearchController {
    
    private PresidentReplySearchService presidentReplySearchService;
    
    private PresidentReplySearchRepresentationModelAssembler assembler;
    
    @Autowired
    public PresidentReplySearchController(
        PresidentReplySearchService presidentReplySearchService,
        PresidentReplySearchRepresentationModelAssembler assembler
    ) {
        this.presidentReplySearchService = presidentReplySearchService;
        this.assembler = assembler;
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<ReplySearch>>> getReplies(ServerWebExchange exchange) {
        return exchange.getPrincipal().flatMap(principal -> {
            Flux<ReplySearch> replySearchFlux = presidentReplySearchService.getRepliesByUser(principal.getName())
                .sort(Comparator.comparing(ReplySearch::getDate).reversed());
            
            return assembler.toCollectionModel(replySearchFlux, exchange);
        });
    }
}
