package com.xebia.interview.ps.exception;

import org.springframework.http.HttpStatus;

public class PaymentServiceException extends RuntimeException{
    private HttpStatus status;

    public PaymentServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
