package com.xebia.interview.ps.exception;

import org.springframework.http.HttpStatus;

public class UserAccountNoNotFoundException extends RuntimeException{
    private HttpStatus status;

    public UserAccountNoNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
