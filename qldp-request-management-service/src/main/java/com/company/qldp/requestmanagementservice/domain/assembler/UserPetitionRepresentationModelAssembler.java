package com.company.qldp.requestmanagementservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Petition;
import com.company.qldp.elasticsearchservice.web.UserPetitionSearchController;
import com.company.qldp.requestmanagementservice.web.UserPetitionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class UserPetitionRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Petition> {
    
    public UserPetitionRepresentationModelAssembler() {
        super(UserPetitionController.class);
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
        return linkTo(methodOn(UserPetitionSearchController.class).getPetitions(exchange), exchange);
    }
}
