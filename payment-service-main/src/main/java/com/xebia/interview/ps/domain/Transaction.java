package com.xebia.interview.ps.domain;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "payment_amy")
    private double paymentAmt;
    @Column(name = "from_acc")
    private String fromAcc;
    @Column(name = "to_acc")
    private String toAcc;
    @Column(name = "note")
    private String note;

    public Transaction() {
    }

    public Transaction(double paymentAmt, String fromAcc, String toAcc, String note) {
        this.paymentAmt = paymentAmt;
        this.toAcc = toAcc;
        this.fromAcc = fromAcc;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public double getPaymentAmt() {
        return paymentAmt;
    }

    public String getToAcc() {
        return toAcc;
    }

    public String getFromAcc() {
        return fromAcc;
    }

    public String getNote() {
        return note;
    }
}
