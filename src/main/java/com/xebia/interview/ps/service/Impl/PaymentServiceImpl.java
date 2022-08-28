package com.xebia.interview.ps.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xebia.interview.ps.constant.Constant;
import com.xebia.interview.ps.constant.Status;
import com.xebia.interview.ps.entities.TransactionHistory;
import com.xebia.interview.ps.exception.InsufficientBalanceException;
import com.xebia.interview.ps.exception.PaymentServiceException;
import com.xebia.interview.ps.exception.UserAccountNoNotFoundException;
import com.xebia.interview.ps.exception.WrongSecretPINException;
import com.xebia.interview.ps.repository.TransactionRepository;
import com.xebia.interview.ps.request.PaymentRequest;
import com.xebia.interview.ps.request.User;
import com.xebia.interview.ps.response.TransactionResponse;
import com.xebia.interview.ps.service.PaymentService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.http.HttpClient;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public TransactionResponse makePayment(PaymentRequest request) throws Exception {
        String fromAcc = request.getFromAcc();
        String toAcc = request.getToAcc();
        if(fromAcc.equals(toAcc)!=true){
        Double paymentAmt = request.getPaymentAmt();
        logger.info("Making payment of {} from {} to {}", paymentAmt, fromAcc, toAcc);
        secretPINAuthentication(request.getSecretPIN(),fromAcc);
        // debit amount from fromAcc
        HttpClient httpClient = HttpClient.newBuilder().build();
        DebitFromSenderAccount(paymentAmt,fromAcc,httpClient);

        // credit amount to toAcc
        CreditToReceiverAccount(paymentAmt,toAcc,httpClient);
        User user = getUserData(fromAcc);
        TransactionHistory saved = transactionRepository.save(
                new TransactionHistory(
                        paymentAmt,
                        fromAcc,
                        toAcc,
                        request.getNote(),
                        Status.SUCCESS
                )
        );
        logger.info("Saved transaction with id {}", saved.getId());
        TransactionResponse transactionResponse=new TransactionResponse();
        transactionResponse.setTransactionHistory(saved);
        transactionResponse.setBalance(user.getBalance());
        transactionResponse.setTransactionNote(paymentAmt+" Rs is Debited from your Account.");
        return transactionResponse;
    }else {
            throw new PaymentServiceException(HttpStatus.BAD_REQUEST,"Sender and Receiver Account Number Can't be same");
        }
    }

    private void secretPINAuthentication(String secretPIN, String fromAcc) throws IOException {
        User user=getUserData(fromAcc);
        String userSecretPIN = user.getSecretPIN();
        if(userSecretPIN.equals(secretPIN)==false){
            throw new WrongSecretPINException(HttpStatus.BAD_REQUEST,"Wrong user secret PIN");
        }
    }

    private User getUserData(String fromAcc) throws IOException {
        try {
            HttpGet request = new HttpGet(Constant.BASE_URL + "/payments/users?accountNo=" + fromAcc);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            User[] users = gson.fromJson(result, User[].class);
            User user = (User) Array.get(users, 0);
            return user;
        }catch (Exception e){
            throw new UserAccountNoNotFoundException(HttpStatus.BAD_REQUEST,"User not found for the AccountNo : "+fromAcc);
        }
    }

    private void CreditToReceiverAccount(Double paymentAmt, String toAcc, HttpClient httpClient) throws IOException, InterruptedException {
             User user=getUserData(toAcc);
             user.setBalance(user.getBalance()+paymentAmt);
             updateUserData(user);
             logger.info("Credit request status code {}", user);
    }

    private void updateUserData(User user) throws IOException {
        HttpPut httpPut=new HttpPut(Constant.BASE_URL + "/payments/users/"+user.getId());
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        StringEntity stringEntity=new StringEntity(new ObjectMapper().writeValueAsString(user));
        httpPut.setEntity(stringEntity);
        CloseableHttpClient client1 = HttpClients.createDefault();
        client1.execute(httpPut);
    }

    private void DebitFromSenderAccount(Double paymentAmt, String fromAcc, HttpClient httpClient) throws Exception {
        User user = getUserData(fromAcc);
        if(user.getBalance()>=paymentAmt){
            user.setBalance(user.getBalance()-paymentAmt);
            updateUserData(user);
        }else{
            throw new InsufficientBalanceException(HttpStatus.BAD_REQUEST,"Insufficient balance in sender account");
        }
        logger.info("Debit request status code {}", user);
    }
}
