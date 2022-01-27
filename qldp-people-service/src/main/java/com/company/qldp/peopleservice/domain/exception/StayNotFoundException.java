package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class StayNotFoundException extends RuntimeException {
    
    public StayNotFoundException() {
        super("Could not find this stay");
    }
}
