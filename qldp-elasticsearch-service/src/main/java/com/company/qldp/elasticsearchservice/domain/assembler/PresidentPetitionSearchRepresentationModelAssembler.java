package com.company.qldp.elasticsearchservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.web.PetitionSearchController;
import com.company.qldp.elasticsearchservice.web.PresidentPetitionSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PresidentPetitionSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<PetitionSearch> {
    
    public PresidentPetitionSearchRepresentationModelAssembler() {
        super(PetitionSearchController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<PetitionSearch> resource) {
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
