package com.company.qldp.elasticsearchservice.main;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
    "com.company.qldp.elasticsearchservice",
    "com.company.qldp.userservice"
})
@EnableReactiveElasticsearchRepositories(basePackages = {
    "com.company.qldp.elasticsearchservice"
})
public class ElasticsearchServiceConfiguration {
}
