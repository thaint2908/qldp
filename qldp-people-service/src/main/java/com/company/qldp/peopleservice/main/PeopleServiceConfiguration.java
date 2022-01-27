package com.company.qldp.peopleservice.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.company.qldp.peopleservice",
    "com.company.qldp.userservice",
    "com.company.qldp.elasticsearchservice",
    "com.company.qldp.householdservice"
})
@EntityScan(basePackages = {"com.company.qldp.domain"})
public class PeopleServiceConfiguration {
}
