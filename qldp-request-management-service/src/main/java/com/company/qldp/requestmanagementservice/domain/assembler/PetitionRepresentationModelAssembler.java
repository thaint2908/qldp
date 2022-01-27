package com.company.qldp.requestmanagementservice.domain.assembler;

import com.company.qldp.common.Status;
import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.Petition;
import com.company.qldp.elasticsearchservice.web.PetitionSearchController;
import com.company.qldp.requestmanagementservice.web.PetitionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Function;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PetitionRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<Petition> {
    
    public PetitionRepresentationModelAssembler() {
        super(PetitionController.class);
    }
    
    @Override
    public EntityModel<Petition> addLinks(EntityModel<Petition> resource, ServerWebExchange exchange) {
        EntityModel<Petition> petitionModel = super.addLinks(resource, exchange);
        
        if (resource.getContent().getBody().getStatus() == Status.PENDING) {
            linkTo(methodOn(PetitionController.class).rejectPetition(resource.getContent().getId(), exchange), exchange)
                .withRel("reject").toMono()
                .map((Function<Link, Object>) petitionModel::add).subscribe();
            
            linkTo(methodOn(PetitionController.class).acceptPetition(resource.getContent().getId(), exchange), exchange)
                .withRel("accept").toMono()
                .map((Function<Link, Object>) petitionModel::add).subscribe();
        }
        
        return petitionModel;
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
        return linkTo(methodOn(PetitionSearchController.class).getPetitions(exchange), exchange);
    }
}
