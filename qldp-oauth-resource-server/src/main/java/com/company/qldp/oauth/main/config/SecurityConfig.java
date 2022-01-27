package com.company.qldp.oauth.main.config;

import com.company.qldp.oauth.domain.converter.KeycloakRealmRoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import reactor.core.publisher.Mono;

import java.util.List;

@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            
            configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8000"));
            configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setExposedHeaders(List.of("X-Auth-Token", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
            
            return configuration;
        }
        )
                .and()
                    .authorizeExchange()
                        .pathMatchers(HttpMethod.POST, "/login")
                            .permitAll()
                        .pathMatchers(HttpMethod.PUT, "/reset-password")
                            .permitAll()
                        .pathMatchers(HttpMethod.GET, "/presidents/replies/**", "/presidents/petitions/**")
                            .hasRole("president")
                        .pathMatchers(HttpMethod.GET, "/users/petitions/**", "/users/replies/**")
                            .hasRole("user")
                        .pathMatchers(HttpMethod.GET, "/users/**")
                            .hasRole("admin")
                        .pathMatchers(HttpMethod.POST, "/users/**")
                            .hasRole("admin")
                        .pathMatchers(HttpMethod.GET, "/people/**")
                            .hasAnyRole("manager", "admin")
                        .pathMatchers(HttpMethod.POST, "/people/**")
                            .hasRole("manager")
                        .pathMatchers(HttpMethod.PATCH, "/people/**")
                            .hasRole("manager")
                        .pathMatchers(HttpMethod.POST, "/petitions")
                            .hasRole("user")
                        .pathMatchers(HttpMethod.GET, "/petitions/**")
                            .hasRole("manager")
                        .pathMatchers(HttpMethod.PATCH, "/petitions/**")
                            .hasRole("manager")
                        .pathMatchers(HttpMethod.DELETE, "/petitions/**")
                            .hasRole("manager")
                        .pathMatchers(HttpMethod.POST, "/replies")
                            .hasRole("president")
                        .pathMatchers(HttpMethod.GET, "/replies/**")
                            .hasRole("manager")
                        .pathMatchers(HttpMethod.PATCH, "/replies/**")
                            .hasRole("manager")
                        .anyExchange()
                            .authenticated()
                .and()
                    .oauth2ResourceServer()
                        .jwt()
                            .jwtAuthenticationConverter(jwtConverter())
                .and()
                .and()
                    .csrf()
                        .requireCsrfProtectionMatcher(webExchangeMatcher());
        
        return http.build();
    }
    
    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        
        return converter;
    }
    
    private ServerWebExchangeMatcher webExchangeMatcher() {
        return new NegatedServerWebExchangeMatcher(
            ServerWebExchangeMatchers.pathMatchers("/**")
        );
    }
}
