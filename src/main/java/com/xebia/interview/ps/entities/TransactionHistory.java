package com.xebia.interview.ps.entities;

import com.xebia.interview.ps.constant.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "payment_amt")
    private double paymentAmount;
    @Column(name = "from_acc")
    private String senderAccNo;
    @Column(name = "to_acc")
    private String receiverAccNo;
    @Column(name = "note")
    private String note;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public TransactionHistory(Double paymentAmt, String fromAcc, String toAcc, String note, Status status) {
        this.paymentAmount=paymentAmt;
        this.receiverAccNo=toAcc;
        this.senderAccNo=fromAcc;
        this.status=status;
        this.note=note;
    }
}
