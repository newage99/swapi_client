package com.example.swapiclient.models;

import java.util.ArrayList;

public abstract class Transport {

    public String name;
    String manufacturer;
    String costInCredits;
    ArrayList<String> pilotsNames;

    Transport(String name, String manufacturer, String costInCredits, ArrayList<String> pilotsNames) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.costInCredits = costInCredits;
        this.pilotsNames = pilotsNames;
    }

    public String getType() {
        return "";
    }

    public String toHtmlString() {
        return "";
    }
}
