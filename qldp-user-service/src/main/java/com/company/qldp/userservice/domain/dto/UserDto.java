package com.company.qldp.userservice.domain.dto;

import com.company.qldp.userservice.domain.validation.password.ValidPassword;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
public class UserDto {
    
    @NotNull
    @Length(message = "Name must not be empty", min = 1)
    private String username;
    
    @NotNull
    @Email(message = "Invalid email")
    private String email;
    
    @ValidPassword
    private String password;
    
    @NotNull
    private String roles;
    
    public String[] getRoles() {
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }
}
