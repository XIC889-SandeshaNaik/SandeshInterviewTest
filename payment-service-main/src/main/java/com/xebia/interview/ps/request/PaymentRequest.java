package com.xebia.interview.ps.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest {
    private final Double paymentAmt;
    private final String fromAcc;
    private final String toAcc;
    private final String note;

    @JsonCreator
    public PaymentRequest(@JsonProperty Double paymentAmt,
                          @JsonProperty String fromAcc,
                          @JsonProperty String toAcc,
                          @JsonProperty String note) {
        this.paymentAmt = paymentAmt;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.note = note;
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
}

