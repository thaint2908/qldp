package com.company.qldp.requestmanagementservice.web;

import com.company.qldp.domain.Reply;
import com.company.qldp.requestmanagementservice.domain.assembler.UserReplyRepresentationModelAssembler;
import com.company.qldp.requestmanagementservice.domain.service.UserReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(
    path = "/users/replies",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class UserReplyController {
    
    private UserReplyService userReplyService;
    
    private UserReplyRepresentationModelAssembler assembler;
    
    @Autowired
    public UserReplyController(
        UserReplyService userReplyService,
        UserReplyRepresentationModelAssembler assembler
    ) {
        this.userReplyService = userReplyService;
        this.assembler = assembler;
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<Reply>> getReply(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        return exchange.getPrincipal().flatMap(principal -> {
            Reply reply = userReplyService.getReply(principal.getName(), id);
            
            return assembler.toModel(reply, exchange);
        });
    }
}
