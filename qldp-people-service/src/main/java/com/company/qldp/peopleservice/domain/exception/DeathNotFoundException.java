package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DeathNotFoundException extends RuntimeException {
    
    public DeathNotFoundException() {
        super("Could not find this death");
    }
}
