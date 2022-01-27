package com.company.qldp.householdservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Household;
import com.company.qldp.elasticsearchservice.web.HouseholdSearchController;
import com.company.qldp.householdservice.web.HouseholdController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class HouseholdRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Household> {
    
    public HouseholdRepresentationModelAssembler() {
        super(HouseholdController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Household> resource) {
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
