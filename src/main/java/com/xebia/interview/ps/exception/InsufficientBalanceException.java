package com.xebia.interview.ps.exception;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends RuntimeException {
    private HttpStatus status;

    public InsufficientBalanceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

