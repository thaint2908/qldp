package com.company.qldp.requestmanagementservice.web;

import com.company.qldp.domain.Reply;
import com.company.qldp.requestmanagementservice.domain.assembler.PresidentReplyRepresentationModelAssembler;
import com.company.qldp.requestmanagementservice.domain.service.PresidentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/presidents/replies",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class PresidentReplyController {
    
    private PresidentReplyService presidentReplyService;
    
    private PresidentReplyRepresentationModelAssembler assembler;
    
    @Autowired
    public PresidentReplyController(
        PresidentReplyService presidentReplyService,
        PresidentReplyRepresentationModelAssembler assembler
    ) {
        this.presidentReplyService = presidentReplyService;
        this.assembler = assembler;
    }
    
    @GetMapping(path = "/{id}")
    public Mono<EntityModel<Reply>> getReply(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        return exchange.getPrincipal().flatMap(principal -> {
            Reply reply = presidentReplyService.getReply(principal.getName(), id);
            
            return assembler.toModel(reply, exchange);
        });
    }
}
