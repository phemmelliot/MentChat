package com.example.phemmelliot.chat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by phemmelliot on 7/2/18.
 */

public interface CardCheckoutService {
    //@Headers("apiKey" "f12058f6b3978d5a5e865e5a4d281b97fc93f2f7ef0540ffd1bb9aa7b990520f")
    @POST("charge")
    Call<CardCheckoutResponse> createCheckoutResponse(@Header("apiKey") String key,
                                                      @Body CardRequest request);
}
