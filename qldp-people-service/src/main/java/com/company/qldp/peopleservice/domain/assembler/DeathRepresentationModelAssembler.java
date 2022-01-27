package com.company.qldp.peopleservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Death;
import com.company.qldp.peopleservice.web.DeathController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class DeathRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Death> {
    
    public DeathRepresentationModelAssembler() {
        super(DeathController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Death> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "deaths";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(DeathController.class).getAllDeaths(exchange), exchange);
    }
}
