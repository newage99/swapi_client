package com.example.swapiclient.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://swapi.co/api/")
            .client(new OkHttpClient.Builder().build())
            .build();

    public static <S> S create(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
