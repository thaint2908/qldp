package com.company.qldp.householdservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Correction;
import com.company.qldp.householdservice.web.CorrectionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class CorrectionRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Correction> {
    
    public CorrectionRepresentationModelAssembler() {
        super(CorrectionController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Correction> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "corrections";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        Map<String, String> attributes = exchange.getAttribute("org.springframework.web.reactive.HandlerMapping.uriTemplateVariables");
        assert attributes != null;
        
        return linkTo(methodOn(CorrectionController.class).getAllCorrections(
            Integer.parseInt(attributes.get("id")),
            exchange
        ), exchange);
    }
}
