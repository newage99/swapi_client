package com.example.swapiclient.services;

import com.example.swapiclient.responses.PeopleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeopleServices {
    @GET("people")
    Call<PeopleResponse> getPeople(@Query("page") String page);
}
