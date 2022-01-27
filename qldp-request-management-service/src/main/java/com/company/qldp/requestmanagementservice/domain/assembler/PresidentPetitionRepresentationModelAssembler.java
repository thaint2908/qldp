package com.company.qldp.requestmanagementservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Petition;
import com.company.qldp.elasticsearchservice.web.PresidentPetitionSearchController;
import com.company.qldp.requestmanagementservice.web.PresidentPetitionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PresidentPetitionRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Petition> {
    
    public PresidentPetitionRepresentationModelAssembler() {
        super(PresidentPetitionController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<Petition> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "petitions";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(PresidentPetitionSearchController.class).getPetitions(exchange), exchange);
    }
}
