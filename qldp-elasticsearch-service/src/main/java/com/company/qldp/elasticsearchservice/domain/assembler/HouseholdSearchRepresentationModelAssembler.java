package com.company.qldp.elasticsearchservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.HouseholdSearch;
import com.company.qldp.elasticsearchservice.web.HouseholdSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class HouseholdSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<HouseholdSearch> {
    
    public HouseholdSearchRepresentationModelAssembler() {
        super(HouseholdSearchController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<HouseholdSearch> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "households";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(HouseholdSearchController.class).getHouseholds(exchange), exchange);
    }
}
