package com.example.phemmelliot.chat;

/**
 * Created by phemmelliot on 7/1/18.
 */

public class ValidateRequest {
    public String username;
    public String transactionId;
    public String otp;
   // public String token;


    public ValidateRequest() {

    }

    public ValidateRequest(String username, String transactionId, String otp) {
        this.username = username;
        this.transactionId = transactionId;
        this.otp = otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
