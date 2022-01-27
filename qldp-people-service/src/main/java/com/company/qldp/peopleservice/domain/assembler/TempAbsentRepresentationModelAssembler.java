package com.company.qldp.peopleservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.TempAbsent;
import com.company.qldp.peopleservice.web.TempAbsentController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class TempAbsentRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<TempAbsent> {
    
    public TempAbsentRepresentationModelAssembler() {
        super(TempAbsentController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<TempAbsent> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "tempAbsents";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(TempAbsentController.class).getAllTempAbsents(exchange), exchange);
    }
}
