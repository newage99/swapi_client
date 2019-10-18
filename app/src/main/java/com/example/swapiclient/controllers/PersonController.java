package com.example.swapiclient.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

import androidx.appcompat.app.AlertDialog;
import com.example.swapiclient.activities.PersonActivity;
import com.example.swapiclient.models.Person;
import com.example.swapiclient.models.Starship;
import com.example.swapiclient.models.Transport;
import com.example.swapiclient.models.Vehicle;
import com.example.swapiclient.responses.SpeciesResponse;
import com.example.swapiclient.responses.VehicleOrStarshipResponse;
import com.example.swapiclient.services.Service;
import com.example.swapiclient.services.SpeciesServices;
import com.example.swapiclient.services.VehiclesAndStarshipsServices;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonController {

    private final static ArrayList<String> species = new ArrayList<>();
    public static ArrayList<Transport> vehicleOrStarshipArray = new ArrayList<>();
    private static int numberOfPetitionsAnswered = 0;
    private static int numberOfSpeciesEndpoints;

    public static void generateSpeciesVehiclesAndStarshipsLists(
            ArrayList<String> speciesEndpoints,
            ArrayList<String> vehiclesEndpoints,
            ArrayList<String> starshipsEndpoints
    ) {
        generateSpeciesList(speciesEndpoints);
        vehicleOrStarshipArray.clear();
        retrieveVehiclesAndAddToList(vehiclesEndpoints);
        retrieveStarshipsAndAddToList(starshipsEndpoints);
    }

    public static void showDialog(Context context, int arrayPosition) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(Html.fromHtml(vehicleOrStarshipArray.get(arrayPosition).toHtmlString()));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private static void generateSpeciesList(ArrayList<String> speciesEndpoints) {
        numberOfSpeciesEndpoints = speciesEndpoints.size();
        species.clear();
        final SpeciesServices speciesServices = Service.create(SpeciesServices.class);
        for (String endpoint : speciesEndpoints) {
            speciesServices.getSpecies(endpoint.substring(endpoint.indexOf("species/") + 8, endpoint.length() - 1))
                    .enqueue(new Callback<SpeciesResponse>() {
                @Override
                public void onResponse(Call<SpeciesResponse> call, Response<SpeciesResponse> response) {
                    if (response.isSuccessful()) {
                        SpeciesResponse body = response.body();
                        if (body != null && body.name != null) {
                            species.add(body.name);
                        }
                    }
                    setPersonSpeciesIfFinished();
                }

                @Override
                public void onFailure(Call<SpeciesResponse> call, Throwable t) {
                    setPersonSpeciesIfFinished();
                }
            });
        }
    }

    private static void setPersonSpeciesIfFinished() {
        numberOfPetitionsAnswered++;
        if (numberOfPetitionsAnswered >= numberOfSpeciesEndpoints)
            PersonActivity.setPersonSpecies(String.join(", ", species));
    }

    private static void retrieveVehiclesAndAddToList(ArrayList<String> vehiclesEndpoints) {
        final VehiclesAndStarshipsServices service = Service.create(VehiclesAndStarshipsServices.class);
        for (String endpoint : vehiclesEndpoints) {
            service.getVehicle(endpoint.substring(endpoint.indexOf("vehicles/") + 9, endpoint.length() - 1))
                    .enqueue(new Callback<VehicleOrStarshipResponse>() {
                @Override
                public void onResponse(Call<VehicleOrStarshipResponse> call, Response<VehicleOrStarshipResponse> response) {
                    if (response.isSuccessful()) {
                        VehicleOrStarshipResponse body = response.body();
                        if (body != null) {
                            addVehicleOrStarship(new Vehicle(body.name,
                                                             body.manufacturer,
                                                             body.cost_in_credits,
                                                             pilotsUrlsToPilotsNames(new ArrayList<>(body.pilots))));
                        }
                    }
                }

                @Override
                public void onFailure(Call<VehicleOrStarshipResponse> call, Throwable t) {}
            });
        }
    }

    private static void retrieveStarshipsAndAddToList(ArrayList<String> starshipsEndpoints) {
        final VehiclesAndStarshipsServices service = Service.create(VehiclesAndStarshipsServices.class);
        for (String endpoint : starshipsEndpoints) {
            service.getStarship(endpoint.substring(endpoint.indexOf("starships/") + 10, endpoint.length() - 1))
                    .enqueue(new Callback<VehicleOrStarshipResponse>() {
                @Override
                public void onResponse(Call<VehicleOrStarshipResponse> call, Response<VehicleOrStarshipResponse> response) {
                    if (response.isSuccessful()) {
                        VehicleOrStarshipResponse body = response.body();
                        if (body != null) {
                            addVehicleOrStarship(new Starship(body.name,
                                                              body.manufacturer,
                                                              body.cost_in_credits,
                                                              pilotsUrlsToPilotsNames(new ArrayList<>(body.pilots))));
                        }
                    }
                }

                @Override
                public void onFailure(Call<VehicleOrStarshipResponse> call, Throwable t) {}
            });
        }
    }

    private static ArrayList<String> pilotsUrlsToPilotsNames(ArrayList<String> pilotsUrls) {
        ArrayList<String> pilotsNames = new ArrayList<>();
        for(Person person : PeopleController.peopleArray) {
            if (pilotsUrls.contains(person.url)) {
                pilotsNames.add(person.name);
            }
        }
        return pilotsNames;
    }

    private static void addVehicleOrStarship(Transport vehicleOrStarship) {
        vehicleOrStarshipArray.add(vehicleOrStarship);
        PersonActivity.notifyVehicleOrShartshipChanged();
    }
}
