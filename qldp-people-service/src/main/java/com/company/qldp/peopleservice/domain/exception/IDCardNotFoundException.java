package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class IDCardNotFoundException extends RuntimeException {
    
    public IDCardNotFoundException() {
        super("Could not find id card for this person");
    }
}
