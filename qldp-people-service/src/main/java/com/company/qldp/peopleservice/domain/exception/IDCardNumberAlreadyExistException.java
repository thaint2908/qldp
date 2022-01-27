package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class IDCardNumberAlreadyExistException extends RuntimeException {
    
    public IDCardNumberAlreadyExistException() {
        super("This id card number already exists");
    }
}
