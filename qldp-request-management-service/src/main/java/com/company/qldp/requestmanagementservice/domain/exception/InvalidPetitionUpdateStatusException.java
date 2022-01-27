package com.company.qldp.requestmanagementservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class InvalidPetitionUpdateStatusException extends RuntimeException {
    
    public InvalidPetitionUpdateStatusException(String status) {
        super("Could not perform update status in petition with status: " + status);
    }
}
