package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class PersonAlreadyExistException extends RuntimeException {
    
    public PersonAlreadyExistException() {
        super("This people already exist");
    }
}
