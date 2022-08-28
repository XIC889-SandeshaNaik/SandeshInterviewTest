package com.xebia.interview.ps.response;

import lombok.Data;

@Data
public class TransactionFailedResponse {
    private String note;
    private String reason;

}
