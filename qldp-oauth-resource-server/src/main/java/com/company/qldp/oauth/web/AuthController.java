package com.company.qldp.oauth.web;

import com.company.qldp.domain.User;
import com.company.qldp.oauth.domain.dto.ResetPasswordDto;
import com.company.qldp.oauth.domain.exception.InvalidCredentialException;
import com.company.qldp.common.util.GenericResponse;
import com.company.qldp.oauth.domain.util.GetTokenResponse;
import com.company.qldp.oauth.domain.util.LoginRequest;
import com.company.qldp.oauth.domain.util.LoginResponse;
import com.company.qldp.oauth.main.properties.ResourceServerProperties;
import com.company.qldp.common.exception.UnknownException;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    
    private UserService userService;
    
    private WebClient webClient;
    
    private ResourceServerProperties properties;
    
    @Autowired
    public AuthController(
        UserService userService,
        WebClient webClient,
        ResourceServerProperties properties
    ) {
        this.userService = userService;
        this.webClient = webClient;
        this.properties = properties;
    }
    
    @PostMapping(
        path = "/login",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<LoginResponse>> login(@Valid LoginRequest loginRequest) {
        User user = userService.findUserByEmail(loginRequest.getEmail());
        
        if (user == null) {
            return Mono.error(new UserNotFoundException());
        }
        
        MultiValueMap<String, String> loginForm = formLogin(user.getUsername(), loginRequest.getPassword());
        
        return webClient.post()
            .uri("/realms/master/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(loginForm)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, (ex) -> {
                if (ex.rawStatusCode() == 401 || ex.rawStatusCode() == 400) {
                    return Mono.error(new InvalidCredentialException());
                }
                
                return Mono.error(new UnknownException(ex.toString()));
            })
            .bodyToMono(GetTokenResponse.class)
            .flatMap(tokenResponse -> {
                LoginResponse response = LoginResponse.builder()
                    .accessToken(tokenResponse.getAccessToken())
                    .expiresIn(tokenResponse.getExpiresIn())
                    .refreshToken(tokenResponse.getRefreshToken())
                    .build();
                
                return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
            });
    }
    
    @PutMapping(
        path = "/reset-password",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Mono<ResponseEntity<GenericResponse>> sendEmailForgotPassword(
        @Valid ResetPasswordDto resetPasswordDto
    ) {
        String email = resetPasswordDto.getEmail();
        User user = userService.findUserByEmail(email);
        
        if (user == null) {
            return Mono.error(new UserNotFoundException());
        }
        
        String keycloakUid = user.getKeycloakUid();
        
        MultiValueMap<String, String> loginForm = formLogin(
            properties.getAdminName(),
            properties.getAdminPassword()
        );
        List<String> actions = new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        
        return webClient.post()
            .uri("/realms/master/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(loginForm)
            .retrieve()
            .bodyToMono(GetTokenResponse.class)
            .flatMap(tokenResponse -> {
                return Mono.just(tokenResponse.getAccessToken());
            })
            .flatMap(accessToken -> {
                return webClient.put()
                    .uri("/admin/realms/master/users/{id}/execute-actions-email", keycloakUid)
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(actions.toArray())
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(responseEntity -> {
                        if (responseEntity.getStatusCodeValue() != 204) {
                            return Mono.error(new UnknownException());
                        }
                        
                        return Mono.just(new ResponseEntity<>(
                            new GenericResponse("Send email success"),
                            HttpStatus.OK
                        ));
                    });
            });
    }
    
    private MultiValueMap<String, String> formLogin(String username, String password) {
        MultiValueMap<String, String> loginForm = new LinkedMultiValueMap<>();
        loginForm.add("grant_type", "password");
        loginForm.add("client_id", properties.getClientId());
        loginForm.add("client_secret", properties.getClientSecret());
        loginForm.add("username", username);
        loginForm.add("password", password);
        
        return loginForm;
    }
}
