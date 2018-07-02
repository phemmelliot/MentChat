package com.example.phemmelliot.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phemmelliot on 7/2/18.
 */

public class CardRequest {
    @Expose
    @SerializedName("username")
    public String username;
    @Expose
    @SerializedName("productName")
    public String productName;
    @Expose
    @SerializedName("paymentCard")
    public PaymentCard paymentCard;
    @Expose
    @SerializedName("currencyCode")
    public String currencyCode;
    @Expose
    @SerializedName("amount")
    public String amount;
    @Expose
    @SerializedName("narration")
    public String narration;

    public CardRequest() {

    }

    public CardRequest(String username, String productName, PaymentCard paymentCard, String currencyCode, String amount, String narration) {
        this.username = username;
        this.productName = productName;
        this.paymentCard = paymentCard;
        this.currencyCode = currencyCode;
        this.amount = amount;
        this.narration = narration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
