package com.example.swapiclient.services;

import com.example.swapiclient.responses.PlanetResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlanetsServices {
    @GET("planets/{planet}")
    Call<PlanetResponse> getPlanet(@Path("planet") String planet);
}
