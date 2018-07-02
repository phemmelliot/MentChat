package com.example.phemmelliot.chat;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by phemmelliot on 7/2/18.
 */

public class ApiUtils {
    private static Retrofit retrofit = null;
    public static String BASE_URL =  "https://payments.africastalking.com/card/checkout/";

    public static CardCheckoutService getCardService(){
        return getClient(BASE_URL).create(CardCheckoutService.class);
    }

    public static ValidateService getValidateService(){
        return getClient(BASE_URL).create(ValidateService.class);
    }


    private static Retrofit getClient(String base_url){
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
