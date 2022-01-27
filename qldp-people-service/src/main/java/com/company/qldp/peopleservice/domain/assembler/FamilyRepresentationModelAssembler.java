package com.company.qldp.peopleservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Family;
import com.company.qldp.peopleservice.web.FamilyController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class FamilyRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Family> {
    
    public FamilyRepresentationModelAssembler() {
        super(FamilyController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Family> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "familyList";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute("org.springframework.web.reactive.HandlerMapping.uriTemplateVariables");
        assert attributes != null;
        
        return linkTo(methodOn(FamilyController.class).getFamilies(
            Integer.parseInt(attributes.get("id")),
            exchange
        ), exchange);
    }
}
