package com.company.qldp.userservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NullPasswordException extends RuntimeException {
    
    public NullPasswordException() {
        super("Password must not be null");
    }
}
