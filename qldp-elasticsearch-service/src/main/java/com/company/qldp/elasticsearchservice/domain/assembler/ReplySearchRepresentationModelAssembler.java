package com.company.qldp.elasticsearchservice.domain.assembler;

import com.company.qldp.common.Status;
import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.ReplySearch;
import com.company.qldp.elasticsearchservice.web.ReplySearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class ReplySearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<ReplySearch> {
    
    public ReplySearchRepresentationModelAssembler() {
        super(ReplySearchController.class);
    }
    
    @Override
    public EntityModel<ReplySearch> addLinks(EntityModel<ReplySearch> resource, ServerWebExchange exchange) {
        EntityModel<ReplySearch> replySearchModel = super.addLinks(resource, exchange);
        
        if (resource.getContent().getStatus() == Status.PENDING) {
            String baseLink = replySearchModel.getLink("self").get().getHref();
            String acceptLink = baseLink + "/accept";
            
            replySearchModel.add(Link.of(acceptLink).withRel("accept"));
        }
        
        return replySearchModel;
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
        return linkTo(methodOn(ReplySearchController.class).getReplies(exchange), exchange);
    }
}
