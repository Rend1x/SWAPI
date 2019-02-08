package com.example.asus.testswa.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://swapi.co/api/";

    private static Retrofit sOurInstance;

    public static Retrofit getInstance() {
        if (sOurInstance == null) {
            sOurInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return sOurInstance;
    }

    private RetrofitClient() {
    }
}
