package com.company.qldp.userservice.domain.dto;

import com.company.qldp.userservice.domain.util.CredentialRepresentation;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakUserDto {
    
    private String username;
    private String email;
    private boolean enabled;
    private CredentialRepresentation[] credentials;
}
