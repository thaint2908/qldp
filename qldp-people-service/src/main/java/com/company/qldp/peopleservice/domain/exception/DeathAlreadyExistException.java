package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class DeathAlreadyExistException extends RuntimeException {
    
    public DeathAlreadyExistException() {
        super("This death already exists");
    }
}
