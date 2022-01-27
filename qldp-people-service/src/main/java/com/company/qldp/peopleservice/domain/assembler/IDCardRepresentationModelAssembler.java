package com.company.qldp.peopleservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.IDCard;
import com.company.qldp.peopleservice.web.IDCardController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class IDCardRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<IDCard> {
    
    public IDCardRepresentationModelAssembler() {
        super(IDCardController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<IDCard> resource) {
        return "";
    }
    
    @Override
    protected String getCollectionName() {
        return "self";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute("org.springframework.web.reactive.HandlerMapping.uriTemplateVariables");
        assert attributes != null;
        
        return linkTo(methodOn(IDCardController.class).getIDCardByPeopleId(
            Integer.parseInt(attributes.get("id")),
            exchange
        ), exchange);
    }
}
