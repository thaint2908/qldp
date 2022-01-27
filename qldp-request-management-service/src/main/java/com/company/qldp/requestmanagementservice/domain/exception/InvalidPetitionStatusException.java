package com.company.qldp.requestmanagementservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class InvalidPetitionStatusException extends RuntimeException {
    
    public InvalidPetitionStatusException() {
        super("Could not perform action to this petition");
    }
}
