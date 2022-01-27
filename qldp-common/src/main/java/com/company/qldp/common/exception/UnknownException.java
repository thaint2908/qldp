package com.company.qldp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownException extends RuntimeException {
    
    public UnknownException() {
    }
    
    public UnknownException(String message) {
        super("An error occurred: " + message);
    }
}
