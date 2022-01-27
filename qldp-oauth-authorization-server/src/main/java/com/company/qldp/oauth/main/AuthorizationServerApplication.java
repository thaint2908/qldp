package com.company.qldp.oauth.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
public class AuthorizationServerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }
    
}
