package com.company.qldp.requestmanagementservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Reply;
import com.company.qldp.elasticsearchservice.web.PresidentReplySearchController;
import com.company.qldp.requestmanagementservice.web.PresidentReplyController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PresidentReplyRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Reply> {
    
    public PresidentReplyRepresentationModelAssembler() {
        super(PresidentReplyController.class);
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
        return linkTo(methodOn(PresidentReplySearchController.class).getReplies(exchange), exchange);
    }
}
