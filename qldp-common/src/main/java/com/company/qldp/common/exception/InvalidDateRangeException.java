package com.company.qldp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidDateRangeException extends RuntimeException {
    
    public InvalidDateRangeException() {
        super("Invalid date range");
    }
}
