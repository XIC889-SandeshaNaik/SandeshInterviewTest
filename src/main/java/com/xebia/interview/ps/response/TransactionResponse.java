package com.xebia.interview.ps.response;

import com.xebia.interview.ps.entities.TransactionHistory;
import lombok.Data;

@Data
public class TransactionResponse {
    private String transactionNote;
    private Double balance;
    private TransactionHistory transactionHistory;
}
