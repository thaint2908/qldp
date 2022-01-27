package com.company.qldp.elasticsearchservice.web;

import com.company.qldp.elasticsearchservice.domain.assembler.ReplySearchRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.domain.service.ReplySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
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
    path = "/replies",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class ReplySearchController {
    
    private ReplySearchService replySearchService;
    
    private ReplySearchRepresentationModelAssembler assembler;
    
    @Autowired
    public ReplySearchController(
        ReplySearchService replySearchService,
        ReplySearchRepresentationModelAssembler assembler
    ) {
        this.replySearchService = replySearchService;
        this.assembler = assembler;
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<ReplySearch>>> getReplies(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
    
        Flux<ReplySearch> replySearchFlux = replySearchService.getRepliesByFilters(queryParams)
            .sort(Comparator.comparing(ReplySearch::getDate));
        
        return assembler.toCollectionModel(replySearchFlux, exchange);
    }
}
