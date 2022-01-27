package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class PersonAlreadyHasIDCardException extends RuntimeException {
    
    public PersonAlreadyHasIDCardException() {
        super("This person already has an id card");
    }
}
