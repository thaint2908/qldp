package com.company.qldp.householdservice.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.company.qldp.householdservice",
    "com.company.qldp.peopleservice"
})
@EntityScan(basePackages = {"com.company.qldp.domain"})
public class HouseholdServiceConfiguration {
}
