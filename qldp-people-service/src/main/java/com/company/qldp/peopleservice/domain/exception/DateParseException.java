package com.company.qldp.peopleservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DateParseException extends RuntimeException {
    
    public DateParseException(String message) {
        super(message);
    }
}
