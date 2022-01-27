package com.company.qldp.elasticsearchservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.web.PresidentReplySearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PresidentReplySearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<ReplySearch> {
    
    public PresidentReplySearchRepresentationModelAssembler() {
        super(PresidentReplySearchController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<ReplySearch> resource) {
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
