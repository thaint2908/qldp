package com.company.qldp.userservice.web;

import com.company.qldp.domain.Role;
import com.company.qldp.domain.User;
import com.company.qldp.userservice.domain.assembler.UserRepresentationModelAssembler;
import com.company.qldp.userservice.domain.dto.GetRolesDto;
import com.company.qldp.userservice.domain.dto.KeycloakUserDto;
import com.company.qldp.userservice.domain.dto.UserDto;
import com.company.qldp.common.exception.UnknownException;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.service.UserService;
import com.company.qldp.userservice.domain.util.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(
    path = "/users",
    produces = MediaTypes.HAL_JSON_VALUE
)
public class UserController {
    
    private UserService userService;
    
    private WebClient webClient;
    
    private UserRepresentationModelAssembler assembler;
    
    @Autowired
    public UserController(
        UserService userService,
        WebClient webClient,
        UserRepresentationModelAssembler assembler
    ) {
        this.userService = userService;
        this.webClient = webClient;
        this.assembler = assembler;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<ResponseEntity<EntityModel<User>>> createUser(
        @Valid UserDto userDto,
        ServerWebExchange exchange
    ) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        assert bearerToken != null;
        String accessToken = bearerToken.replace("Bearer", "").trim();
        
        List<CredentialRepresentation> credentials = new ArrayList<>();
        credentials.add(
            CredentialRepresentation
                .builder()
                .type("password")
                .value(userDto.getPassword())
                .build()
        );
        
        KeycloakUserDto keycloakUserDto = KeycloakUserDto.builder()
            .username(userDto.getUsername())
            .email(userDto.getEmail())
            .enabled(true)
            .credentials(credentials.toArray(CredentialRepresentation[]::new))
            .build();
    
        return webClient.post().uri("/admin/realms/master/users")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(keycloakUserDto)
            .retrieve()
            .toBodilessEntity()
            .flatMap(responseEntity -> {
                if (responseEntity.getStatusCodeValue() == 201 &&
                    responseEntity.getHeaders().getLocation() != null
                ) {
                    String userId = responseEntity.getHeaders().getLocation()
                        .toString()
                        .replace("http://localhost:8081/auth/admin/realms/master/users/", "");
                    User user = userService.createUser(userDto, userId);
                
                    return webClient.get()
                        .uri("/admin/realms/master/roles")
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .bodyToMono(GetRolesDto[].class)
                        .flatMap(responses -> {
                            String[] userRoles = user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .toArray(String[]::new);
                        
                            List<GetRolesDto> requestBody = new ArrayList<>();
                        
                            for (GetRolesDto response : responses) {
                                for (String name : userRoles) {
                                    if (response.getName().equals(name)) {
                                        requestBody.add(
                                            GetRolesDto
                                                .builder()
                                                .id(response.getId())
                                                .name(response.getName())
                                                .build()
                                        );
                                    }
                                }
                            }
                        
                            return webClient.post()
                                .uri("/admin/realms/master/users/{id}/role-mappings/realm", userId)
                                .header("Authorization", "Bearer " + accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(requestBody.toArray())
                                .retrieve()
                                .toBodilessEntity()
                                .flatMap(entity -> {
                                    if (entity.getStatusCodeValue() == 204) {
                                        return assembler.toModel(user, exchange)
                                            .map(userModel -> ResponseEntity
                                                .created(userModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                                .body(userModel)
                                            );
                                    }
                                
                                    return Mono.error(new UnknownException(entity.getStatusCode().getReasonPhrase()));
                                });
                        });
                }
            
                return Mono.error(new UnknownException(responseEntity.getStatusCode().getReasonPhrase()));
            });
    }
    
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<EntityModel<User>> getUser(
        @PathVariable("id") Integer id,
        ServerWebExchange exchange
    ) {
        User user = userService.findUserById(id);
        
        return assembler.toModel(user, exchange);
    }
    
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Mono<CollectionModel<EntityModel<User>>> getUsers(ServerWebExchange exchange) {
        List<User> users = userService.findUsers();
        
        return assembler.toCollectionModel(Flux.fromIterable(users), exchange);
    }
}
