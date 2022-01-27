package com.company.qldp.oauth.domain.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTokenResponse {
    
    private String access_token;
    private String expires_in;
    private String refresh_token;
    
    public String getAccessToken() {
        return this.access_token;
    }
    
    public String getExpiresIn() {
        return this.expires_in;
    }
    
    public String getRefreshToken() {
        return this.refresh_token;
    }
}
