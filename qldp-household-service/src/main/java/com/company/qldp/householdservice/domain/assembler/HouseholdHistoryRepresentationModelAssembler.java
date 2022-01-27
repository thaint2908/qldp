package com.company.qldp.householdservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.HouseholdHistory;
import com.company.qldp.householdservice.web.HouseholdHistoryController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class HouseholdHistoryRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<HouseholdHistory> {
    
    public HouseholdHistoryRepresentationModelAssembler() {
        super(HouseholdHistoryController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<HouseholdHistory> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "householdHistories";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute("org.springframework.web.reactive.HandlerMapping.uriTemplateVariables");
        assert attributes != null;
        
        return linkTo(methodOn(HouseholdHistoryController.class).getHouseholdHistories(
            Integer.parseInt(attributes.get("id")),
            exchange
        ), exchange);
    }
}
