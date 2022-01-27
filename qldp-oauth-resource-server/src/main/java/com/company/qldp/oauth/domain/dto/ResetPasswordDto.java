package com.company.qldp.oauth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    
    @NotNull
    @Email(message = "Invalid email")
    private String email;
}
