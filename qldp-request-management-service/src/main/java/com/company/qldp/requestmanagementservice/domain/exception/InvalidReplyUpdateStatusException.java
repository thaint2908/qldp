package com.company.qldp.requestmanagementservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class InvalidReplyUpdateStatusException extends RuntimeException {
    
    public InvalidReplyUpdateStatusException(String status) {
        super("Could not perform update status in reply with status: " + status);
    }
}
