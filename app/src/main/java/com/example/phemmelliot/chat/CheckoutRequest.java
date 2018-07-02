package com.example.phemmelliot.chat;

import java.util.Map;

/**
 * Created by phemmelliot on 7/1/18.
 */

public abstract class CheckoutRequest {

    public String productName;
    public float amount;
    public String currencyCode;
    public String narration;
    public Map metadata;

    public transient TYPE type = TYPE.MOBILE;

    public enum TYPE {
        MOBILE,
        CARD,
        BANK,
    }

    CheckoutRequest(String productName, String amount) {
        this.productName = productName;

        try {
            String[] currenciedAmount = amount.trim().split(" ");
            this.currencyCode = currenciedAmount[0];
            this.amount = Float.parseFloat(currenciedAmount[1]);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
