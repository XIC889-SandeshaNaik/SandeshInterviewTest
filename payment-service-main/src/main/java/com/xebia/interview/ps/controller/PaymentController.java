package com.xebia.interview.ps.controller;

import com.xebia.interview.ps.request.PaymentRequest;
import com.xebia.interview.ps.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Void> makePayment(@RequestBody PaymentRequest paymentRequest) throws Exception {
        paymentService.makePayment(paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
