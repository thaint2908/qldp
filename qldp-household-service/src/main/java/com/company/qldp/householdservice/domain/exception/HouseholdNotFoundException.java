package com.company.qldp.householdservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class HouseholdNotFoundException extends RuntimeException {
    
    public HouseholdNotFoundException() {
        super("Could not find this household");
    }
}
