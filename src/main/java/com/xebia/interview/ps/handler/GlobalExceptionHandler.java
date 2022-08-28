package com.xebia.interview.ps.handler;

import com.xebia.interview.ps.exception.InsufficientBalanceException;
import com.xebia.interview.ps.exception.PaymentServiceException;
import com.xebia.interview.ps.exception.UserAccountNoNotFoundException;
import com.xebia.interview.ps.exception.WrongSecretPINException;
import com.xebia.interview.ps.response.TransactionFailedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity InsufficientBalanceException(InsufficientBalanceException e) {
        TransactionFailedResponse response=new TransactionFailedResponse();
        response.setReason(e.getMessage());
        response.setNote("Transaction failed");
       return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(WrongSecretPINException.class)
    public ResponseEntity WrongSecretPINException(WrongSecretPINException e) {
        TransactionFailedResponse response=new TransactionFailedResponse();
        response.setReason(e.getMessage());
        response.setNote("Transaction failed");
        return ResponseEntity.status(e.getStatus()).body(response);
    }

    @ExceptionHandler(UserAccountNoNotFoundException.class)
    public ResponseEntity UserAccountNoNotFoundException(UserAccountNoNotFoundException e) {
        TransactionFailedResponse response=new TransactionFailedResponse();
        response.setReason(e.getMessage());
        response.setNote("Transaction failed");
        return ResponseEntity.status(e.getStatus()).body(response);
    }
    @ExceptionHandler(PaymentServiceException.class)
    public ResponseEntity PaymentServiceException(PaymentServiceException e) {
        TransactionFailedResponse response=new TransactionFailedResponse();
        response.setReason(e.getMessage());
        response.setNote("Transaction failed");
        return ResponseEntity.status(e.getStatus()).body(response);
    }
}
