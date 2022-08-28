package com.xebia.interview.ps.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest {
    private final Double paymentAmt;
    private final String fromAcc;
    private final String toAcc;
    private final String note;
    private final String secretPIN;


    public PaymentRequest( Double paymentAmt,
                           String fromAcc,
                           String toAcc,
                           String note,
                           String secretPIN) {
        this.paymentAmt = paymentAmt;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.note = note;
        this.secretPIN = secretPIN;
    }

    public Double getPaymentAmt() {
        return paymentAmt;
    }

    public String getFromAcc() {
        return fromAcc;
    }

    public String getToAcc() {
        return toAcc;
    }

    public String getNote() {
        return note;
    }

    public String getSecretPIN() {
        return secretPIN;
    }
}

