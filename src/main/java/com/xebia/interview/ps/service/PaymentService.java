package com.xebia.interview.ps.service;

import com.xebia.interview.ps.request.PaymentRequest;
import com.xebia.interview.ps.response.TransactionResponse;

public interface PaymentService {
    TransactionResponse makePayment(PaymentRequest request) throws Exception;
}
