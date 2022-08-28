package com.xebia.interview.ps.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xebia.interview.ps.constant.Role;

public class User {
    private Long id;
    private String accountNo;
    private  Double balance;
    private  String secretPIN;

    @JsonCreator
    public User(@JsonProperty Double balance,
                          @JsonProperty String secretPIN,
                @JsonProperty String accountNo,
                @JsonProperty Long id ) {
        this.balance = balance;
        this.secretPIN = secretPIN;
        this.accountNo= accountNo;
        this.id=id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance){this.balance=balance;}

    public Long getId() {
        return id;
    }

    public String getSecretPIN() {return secretPIN;}

    public String getAccountNo() {
        return accountNo;
    }
}
