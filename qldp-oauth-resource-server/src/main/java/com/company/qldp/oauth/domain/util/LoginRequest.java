package com.company.qldp.oauth.domain.util;

import com.company.qldp.userservice.domain.validation.password.ValidPassword;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    
    @NotNull
    @Email(message = "Invalid email")
    private String email;
    
    @ValidPassword
    private String password;
}
