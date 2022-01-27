package com.company.qldp.userservice.domain.assembler;

import com.company.qldp.common.assembler.SimpleIdentifiableReactiveRepresentationModelAssembler;
import com.company.qldp.domain.User;
import com.company.qldp.userservice.web.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;

@Component
public class UserRepresentationModelAssembler
    extends SimpleIdentifiableReactiveRepresentationModelAssembler<User> {
    
    public UserRepresentationModelAssembler() {
        super(UserController.class);
    }
    
    @Override
    protected String getEntityId(EntityModel<User> resource) {
        return resource.getContent().getId().toString();
    }
    
    @Override
    protected String getCollectionName() {
        return "users";
    }
    
    @Override
    protected WebFluxBuilder initLinkBuilder(ServerWebExchange exchange) {
        return linkTo(methodOn(UserController.class).getUsers(exchange), exchange);
    }
}
