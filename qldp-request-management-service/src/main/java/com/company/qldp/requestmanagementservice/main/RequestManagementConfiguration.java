package com.company.qldp.requestmanagementservice.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.company.qldp.userservice",
    "com.company.qldp.requestmanagementservice",
    "com.company.qldp.elasticsearchservice"
})
@EntityScan(basePackages = {"com.company.qldp.domain"})
public class RequestManagementConfiguration {
}
