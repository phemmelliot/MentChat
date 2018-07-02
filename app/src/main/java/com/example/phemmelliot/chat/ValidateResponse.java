package com.example.phemmelliot.chat;

/**
 * Created by phemmelliot on 7/1/18.
 */

public class ValidateResponse {
    public String status;
    public String description;
    //public String checkoutToken;


    public ValidateResponse() {

    }

    public ValidateResponse(String status, String description) {
        this.status = status;
        this.description = description;
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
}
