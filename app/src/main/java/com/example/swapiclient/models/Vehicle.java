package com.example.swapiclient.models;

import androidx.annotation.NonNull;
import java.util.ArrayList;

public class Vehicle extends Transport {

    public Vehicle(String name,
                   String manufacturer,
                   String costInCredits,
                   ArrayList<String> pilotsNames) {
        super(name, manufacturer, costInCredits, pilotsNames);
    }

    public String getType() {
        return "Vehículo";
    }

    @NonNull
    public String toHtmlString() {
        return "<b>Nombre:</b> " +
                name +
                "<br><b>Fabricante:</b> " +
                manufacturer +
                "\n<br><b>Coste en créditos:</b> " +
                costInCredits +
                "\n<br><b>Pilotos:</b> " +
                String.join(", ", pilotsNames);
    }
}
