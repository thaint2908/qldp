package com.company.qldp.requestmanagementservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Reply;
import com.company.qldp.elasticsearchservice.web.UserReplySearchController;
import com.company.qldp.requestmanagementservice.web.UserReplyController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class UserReplyRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Reply> {
    
    public UserReplyRepresentationModelAssembler() {
        super(UserReplyController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Reply> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "replies";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(UserReplySearchController.class).getReplies(exchange), exchange);
    }
}
