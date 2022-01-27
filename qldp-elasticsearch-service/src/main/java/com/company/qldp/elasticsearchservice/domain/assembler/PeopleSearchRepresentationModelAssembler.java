package com.company.qldp.elasticsearchservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.PeopleSearch;
import com.company.qldp.elasticsearchservice.web.PeopleSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PeopleSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<PeopleSearch> {
    
    public PeopleSearchRepresentationModelAssembler() {
        super(PeopleSearchController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<PeopleSearch> resource) {
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
