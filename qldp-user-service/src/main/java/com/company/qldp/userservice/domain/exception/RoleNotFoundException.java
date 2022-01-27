package com.company.qldp.userservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {
    
    public RoleNotFoundException() {
        super("Could not find given role");
    }
}
