package com.company.qldp.householdservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.FamilyMember;
import com.company.qldp.householdservice.web.FamilyMemberController;
import com.company.qldp.peopleservice.web.FamilyController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class FamilyMemberRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<FamilyMember> {
    
    public FamilyMemberRepresentationModelAssembler() {
        super(FamilyController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<FamilyMember> resource) {
        return resource.getContent().getPerson().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "members";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute("org.springframework.web.reactive.HandlerMapping.uriTemplateVariables");
        assert attributes != null;
        
        return linkTo(methodOn(FamilyMemberController.class).getAllMembers(
            Integer.parseInt(attributes.get("id")),
            exchange
        ), exchange);
    }
}
