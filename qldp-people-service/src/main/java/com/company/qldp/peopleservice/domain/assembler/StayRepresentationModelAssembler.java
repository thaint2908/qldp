package com.company.qldp.peopleservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Stay;
import com.company.qldp.peopleservice.web.StayController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class StayRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Stay> {
    
    public StayRepresentationModelAssembler() {
        super(StayController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Stay> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "stays";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(StayController.class).getAllStays(exchange), exchange);
    }
}
