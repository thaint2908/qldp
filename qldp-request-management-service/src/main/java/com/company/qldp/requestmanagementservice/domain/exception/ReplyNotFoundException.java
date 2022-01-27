package com.company.qldp.requestmanagementservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ReplyNotFoundException extends RuntimeException {
    
    public ReplyNotFoundException() {
        super("Could not find this reply");
    }
}
