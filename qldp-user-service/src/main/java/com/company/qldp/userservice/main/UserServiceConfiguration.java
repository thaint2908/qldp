package com.company.qldp.userservice.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.company.qldp.userservice"})
@EntityScan(basePackages = {"com.company.qldp.domain"})
public class UserServiceConfiguration {
    
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8081/auth");
    }
}
