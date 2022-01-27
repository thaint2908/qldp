package com.company.qldp.oauth.domain.aop;

import com.company.qldp.userservice.domain.exception.NullPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler({NullPasswordException.class})
    public ResponseStatusException handleNullPasswordException(NullPasswordException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    
    @ExceptionHandler({DateTimeParseException.class})
    public ResponseStatusException handleDateTimeException(DateTimeParseException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
    
    @ExceptionHandler({ParseException.class})
    public ResponseStatusException handleParseException(ParseException e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
