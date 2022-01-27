package com.company.qldp.peopleservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.People;
import com.company.qldp.elasticsearchservice.web.PeopleSearchController;
import com.company.qldp.peopleservice.web.PeopleController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PeopleRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<People> {
    
    public PeopleRepresentationModelAssembler() {
        super(PeopleController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<People> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "people";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(PeopleSearchController.class).getPeople(exchange), exchange);
    }
}
