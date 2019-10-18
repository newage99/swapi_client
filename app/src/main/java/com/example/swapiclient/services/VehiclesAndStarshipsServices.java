package com.example.swapiclient.services;

import com.example.swapiclient.responses.PeopleResponse;
import com.example.swapiclient.responses.VehicleOrStarshipResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VehiclesAndStarshipsServices {
    @GET("vehicles/{vehicle}")
    Call<VehicleOrStarshipResponse> getVehicle(@Path("vehicle") String vehicle);

    @GET("starships/{starship}")
    Call<VehicleOrStarshipResponse> getStarship(@Path("starship") String starship);
}
