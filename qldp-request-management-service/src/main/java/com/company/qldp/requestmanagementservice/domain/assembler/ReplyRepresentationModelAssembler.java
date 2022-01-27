package com.company.qldp.requestmanagementservice.domain.assembler;

import com.company.qldp.common.Status;
import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Reply;
import com.company.qldp.elasticsearchservice.web.ReplySearchController;
import com.company.qldp.requestmanagementservice.web.ReplyController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Function;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class ReplyRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Reply> {
    
    public ReplyRepresentationModelAssembler() {
        super(ReplyController.class);
    }
    
    @Override
    public EntityModel<Reply> addLinks(EntityModel<Reply> resource, ServerWebExchange exchange) {
        EntityModel<Reply> replyModel = super.addLinks(resource, exchange);
        
        if (resource.getContent().getBody().getStatus() == Status.PENDING) {
            linkTo(methodOn(ReplyController.class).acceptReply(resource.getContent().getId(), exchange), exchange)
                .withRel("accept").toMono()
                .map((Function<Link, Object>) replyModel::add).subscribe();
        }
        
        return replyModel;
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
        return linkTo(methodOn(ReplySearchController.class).getReplies(exchange), exchange);
    }
}
