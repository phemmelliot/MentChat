package com.example.phemmelliot.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phemmelliot on 7/2/18.
 */

public class PaymentCard {
    @Expose
    @SerializedName("number")
    public String number;
    @Expose
    @SerializedName("cvvNumber")
    public int cvvNumber;
    @Expose
    @SerializedName("expiryMonth")
    public int expiryMonth;
    @Expose
    @SerializedName("expiryYear")
    public int expiryYear;
    @Expose
    @SerializedName("countryCode")
    public String countryCode;
    @Expose
    @SerializedName("authToken")
    public String authToken;

    /**
     * A payment card
     * @param number Card number (PAN)
     * @param cvvNumber 3-4 Card Verification Value
     * @param expiryMonth Expiration month on the card. e.g. 11 for November
     * @param expiryYear Card expiration year e.g. 2023
     * @param countryCode <a href="https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2ISO">3166-1 alpha-2</a> country code e.g. NG for Nigeria
     * @param authToken PIN from card owner
     */
    public PaymentCard(String number, int cvvNumber, int expiryMonth, int expiryYear, String countryCode, String authToken) {
        this.number = number;
        this.cvvNumber = cvvNumber;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.countryCode = countryCode;
        this.authToken = authToken;
    }

    public PaymentCard() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(int cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
