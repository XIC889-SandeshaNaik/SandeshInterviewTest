package com.xebia.interview.ps.exception;

import org.springframework.http.HttpStatus;

public class WrongSecretPINException extends RuntimeException{
    private HttpStatus status;

    public WrongSecretPINException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
