package com.company.qldp.userservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistException extends RuntimeException {
    
    private static final long serialVersionUID = 5861310537366287163L;
    
    public UserAlreadyExistException(String field) {
        super("User with this " + field + " already exists!");
    }
}
