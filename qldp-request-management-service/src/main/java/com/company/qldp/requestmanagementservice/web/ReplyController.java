package com.company.qldp.requestmanagementservice.web;

import com.company.qldp.domain.Reply;
import com.company.qldp.requestmanagementservice.domain.assembler.ReplyRepresentationModelAssembler;
import com.company.qldp.requestmanagementservice.domain.dto.ReplyDto;
import com.company.qldp.requestmanagementservice.domain.service.ReplyService;
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
    path = "/replies",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class ReplyController {
    
    private ReplyService replyService;
    
    private ReplyRepresentationModelAssembler assembler;
    
    @Autowired
    public ReplyController(
        ReplyService replyService,
        ReplyRepresentationModelAssembler assembler
    ) {
        this.replyService = replyService;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<Reply>>> createReply(
        @Valid ReplyDto replyDto,
        ServerWebExchange exchange
    ) {
        return exchange.getPrincipal().flatMap(principal -> {
            Reply reply = replyService.createReply(principal.getName(), replyDto);
            
            return assembler.toModel(reply, exchange)
                .map(replyModel -> ResponseEntity
                    .created(replyModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(replyModel)
                );
        });
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Reply>> getReply(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Reply reply = replyService.getReply(id);
        
        return assembler.toModel(reply, exchange);
    }
    
    @PatchMapping("/{id}/accept")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Reply>> acceptReply(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        Reply reply = replyService.acceptReply(id);
        
        return assembler.toModel(reply, exchange);
    }
}
