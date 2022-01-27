package com.company.qldp.householdservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidAddressException extends RuntimeException {
    
    public InvalidAddressException() {
        super("Invalid address");
    }
}
