package com.company.qldp.elasticsearchservice.domain.assembler;

import com.company.qldp.common.Status;
import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.elasticsearchservice.domain.entity.PetitionSearch;
import com.company.qldp.elasticsearchservice.web.PetitionSearchController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class PetitionSearchRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<PetitionSearch> {
    
    public PetitionSearchRepresentationModelAssembler() {
        super(PetitionSearchController.class);
    }
    
    @Override
    public EntityModel<PetitionSearch> addLinks(EntityModel<PetitionSearch> resource, ServerWebExchange exchange) {
        EntityModel<PetitionSearch> petitionSearchModel = super.addLinks(resource, exchange);
        
        if (resource.getContent().getStatus() == Status.PENDING) {
            String baseLink = petitionSearchModel.getLink("self").get().getHref();
            String rejectLink = baseLink + "/reject";
            String acceptLink = baseLink + "/accept";
            
            petitionSearchModel.add(Link.of(rejectLink).withRel("reject"));
            petitionSearchModel.add(Link.of(acceptLink).withRel("accept"));
        }
        
        return petitionSearchModel;
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
        return linkTo(methodOn(PetitionSearchController.class).getPetitions(exchange), exchange);
    }
}
