package com.example.swapiclient.controllers;

import com.example.swapiclient.activities.PeopleActivity;
import com.example.swapiclient.models.Person;
import com.example.swapiclient.responses.PeopleResponse;
import com.example.swapiclient.responses.PersonResponse;
import com.example.swapiclient.services.PeopleServices;
import com.example.swapiclient.services.Service;
import java.util.ArrayList;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeopleController {

    private static PeopleServices peopleServices;
    public static ArrayList<Person> peopleArray = new ArrayList<>();

    public static void generatePeopleList() {
        peopleServices = Service.create(PeopleServices.class);
        retrievePeople(null);
    }

    private static void retrievePeople(String page) {
        peopleServices.getPeople(page).enqueue(new Callback<PeopleResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                if (response.isSuccessful()) {
                    PeopleResponse body = response.body();
                    if (body != null) {
                        String page = getPageNumberFromEndpoint(body.next);
                        if (page != null) retrievePeople(page);
                        ArrayList<PersonResponse> peopleResponse = new ArrayList<>(body.results);
                        ArrayList<Person> people = new ArrayList<>();
                        for (int i = 0; i < peopleResponse.size(); i++) {
                            PersonResponse personResponse = peopleResponse.get(i);
                            people.add(new Person(personResponse.name,
                                                  personResponse.gender,
                                                  personResponse.homeworld,
                                                  personResponse.birth_year,
                                                  new ArrayList<>(personResponse.species),
                                                  new ArrayList<>(personResponse.vehicles),
                                                  new ArrayList<>(personResponse.starships),
                                                  personResponse.url));
                        }
                        addPeople(people);
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<PeopleResponse> call, Throwable t) {}
        });
    }

    private static String getPageNumberFromEndpoint(String endpoint) {
        if (endpoint != null && endpoint.contains("page")) {
            try {
                String[] queryItems = endpoint.split("\\?")[1].split("&");
                for (String item : queryItems) {
                    if (item.startsWith("page"))
                        return item.split("=")[1];
                }
            } catch (Exception e) {
                System.out.println("getPageNumberFromEndpoint: " + e.toString());
            }
        }
        return null;
    }

    private static void addPeople(ArrayList<Person> people) {
        PeopleController.peopleArray.addAll(people);
        PeopleActivity.notifyPeopleChanged();
    }
}
