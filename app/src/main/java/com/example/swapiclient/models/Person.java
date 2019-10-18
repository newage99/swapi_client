package com.example.swapiclient.models;

import com.example.swapiclient.responses.PlanetResponse;
import com.example.swapiclient.services.PlanetsServices;
import com.example.swapiclient.services.Service;
import java.io.Serializable;
import java.util.ArrayList;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Person implements Serializable {

    public String name;
    public String gender;
    public String homeWorld;
    public String birthYear;
    public ArrayList<String> speciesEndpoints;
    public ArrayList<String> vehiclesEndpoints;
    public ArrayList<String> starshipsEndpoints;
    public ArrayList<Transport> vehicleOrStarships;
    public String url;

    public Person(String name,
                  String gender,
                  String homeWorldEndpoint,
                  String birthYear,
                  ArrayList<String> speciesEndpoints,
                  ArrayList<String> vehiclesEndpoints,
                  ArrayList<String> starshipsEndpoints,
                  String url) {
        this.name = name;
        this.gender = gender;
        retrieveHomeWorldName(homeWorldEndpoint);
        this.birthYear = birthYear;
        this.speciesEndpoints = speciesEndpoints;
        this.vehiclesEndpoints = vehiclesEndpoints;
        this.starshipsEndpoints = starshipsEndpoints;
        this.url = url;
    }

    private void retrieveHomeWorldName(String endpoint) {
        final PlanetsServices planetsServices = Service.create(PlanetsServices.class);
        final Person me = this;
        planetsServices.getPlanet(endpoint.substring(endpoint.indexOf("planets/") + 8, endpoint.length() - 1))
                .enqueue(new Callback<PlanetResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PlanetResponse> call, Response<PlanetResponse> response) {
                if (response.isSuccessful()) {
                    PlanetResponse body = response.body();
                    if (body != null && body.name != null) {
                        me.homeWorld = body.name;
                    }
                } else me.homeWorld = "Desconocido";
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PlanetResponse> call, Throwable t) {
                me.homeWorld = "Desconocido";
            }
        });
    }
}