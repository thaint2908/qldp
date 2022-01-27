package com.company.qldp.oauth.main.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("qldp.app")
public class ResourceServerProperties {
    
    private String clientId;
    private String clientSecret;
    private String adminName;
    private String adminPassword;
}
