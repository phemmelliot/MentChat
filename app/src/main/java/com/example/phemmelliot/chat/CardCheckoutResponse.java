package com.example.phemmelliot.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phemmelliot on 7/1/18.
 */

public class CardCheckoutResponse {
    @Expose
    @SerializedName("status")
    public String status;
    @Expose
    @SerializedName("description")
    public String description;
    @Expose
    @SerializedName("transactionId")
    public String transactionId;
    //public String checkoutToken;


    public CardCheckoutResponse() {

    }

    public CardCheckoutResponse(String status, String description, String transactionId) {
        this.status = status;
        this.description = description;
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
