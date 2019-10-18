package com.example.swapiclient.services;

import com.example.swapiclient.responses.SpeciesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpeciesServices {
    @GET("species/{species}")
    Call<SpeciesResponse> getSpecies(@Path("species") String species);
}
