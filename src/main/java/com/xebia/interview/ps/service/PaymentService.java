package com.xebia.interview.ps.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.interview.ps.domain.Transaction;
import com.xebia.interview.ps.repository.TransactionRepository;
import com.xebia.interview.ps.request.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public void makePayment(PaymentRequest request) throws Exception {
        String paymentSwitchUrl = "http://localhost:9898/payments";
        String fromAcc = request.getFromAcc();
        String toAcc = request.getToAcc();
        Double paymentAmt = request.getPaymentAmt();
        logger.info("Making payment of {} from {} to {}", paymentAmt, fromAcc, toAcc);
        // debit amount from fromAcc
        HttpClient httpClient = HttpClient.newBuilder().build();
        var debitRequestJson = objectMapper.writeValueAsString(
                Map.of("acc", fromAcc,
                        "amt", String.valueOf(paymentAmt)));
        HttpRequest debitRequest = HttpRequest.newBuilder()
                .uri(URI.create(paymentSwitchUrl + "/debit"))
                .POST(HttpRequest.BodyPublishers.ofString(debitRequestJson))
                .build();
        HttpResponse<String> debitResponse = httpClient.send(debitRequest,
                HttpResponse.BodyHandlers.ofString());
        int debitRequestStatusCode = debitResponse.statusCode();
        logger.info("Debit request status code {}", debitRequestStatusCode);

        // credit amount to toAcc
        var creditRequestJson = objectMapper.writeValueAsString(
                Map.of("acc", toAcc,
                        "amt",
                        String.valueOf(paymentAmt)));
        HttpRequest creditRequest = HttpRequest.newBuilder()
                .uri(URI.create(paymentSwitchUrl + "/credit"))
                .POST(HttpRequest.BodyPublishers.ofString(creditRequestJson))
                .build();
        HttpResponse<String> creditResponse = httpClient.send(creditRequest,
                HttpResponse.BodyHandlers.ofString());
        int creditRequestStatusCode = creditResponse.statusCode();
        logger.info("Credit request status code {}", creditRequestStatusCode);

        var saved = transactionRepository.save(
                new Transaction(
                        paymentAmt,
                        fromAcc,
                        toAcc,
                        request.getNote()
                )
        );

        logger.info("Saved transaction with id {}", saved.getId());


    }
}
