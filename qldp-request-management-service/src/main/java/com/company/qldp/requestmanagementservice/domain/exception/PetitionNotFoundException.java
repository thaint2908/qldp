package com.company.qldp.requestmanagementservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PetitionNotFoundException extends RuntimeException {
    
    public PetitionNotFoundException() {
        super("Could not find this petition");
    }
}
