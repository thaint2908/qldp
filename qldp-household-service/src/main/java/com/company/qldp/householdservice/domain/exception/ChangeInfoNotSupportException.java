package com.company.qldp.householdservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ChangeInfoNotSupportException extends RuntimeException {
    
    public ChangeInfoNotSupportException() {
        super("Change info is not supported");
    }
}
