package com.example.phemmelliot.chat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by phemmelliot on 7/2/18.
 */

public interface ValidateService {
    @POST("validate")
    Call<ValidateResponse> createValidateResponse(@Body ValidateRequest request);
}
