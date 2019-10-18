package com.example.swapiclient.models;

import androidx.annotation.NonNull;
import java.util.ArrayList;

public class Starship extends Transport {

    public Starship(String name,
                   String manufacturer,
                   String costInCredits,
                   ArrayList<String> pilotsEndpoints) {
        super(name, manufacturer, costInCredits, pilotsEndpoints);
    }

    public String getType() {
        return "Nave Espacial";
    }

    @NonNull
    public String toHtmlString() {
        return "<b>Nombre:</b> " +
                name +
                "\n<br><b>Fabricante:</b> " +
                manufacturer +
                "\n<br><b>Coste en créditos:</b> " +
                costInCredits +
                "\n<br><b>Pilotos:</b> " +
                String.join(", ", pilotsNames);
    }
}
