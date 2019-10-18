package com.example.swapiclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.swapiclient.R;
import com.example.swapiclient.adapters.VehiclesOrStarshipsArrayAdapter;
import com.example.swapiclient.controllers.PersonController;
import com.example.swapiclient.models.Person;

public class PersonActivity extends AppCompatActivity {

    private static PersonActivity context;
    private Person person;
    private VehiclesOrStarshipsArrayAdapter vehiclesOrStarshipsAdapter;
    private TextView species;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        person = (Person) getIntent().getSerializableExtra("Person");
        setContentView(R.layout.activity_person);
        ((TextView) findViewById(R.id.personName)).setText(person.name);
        ((TextView) findViewById(R.id.personGender)).setText(person.gender);
        ((TextView) findViewById(R.id.personBirthYear)).setText(person.birthYear);
        ((TextView) findViewById(R.id.personHomeWorld)).setText(person.homeWorld);
        species = findViewById(R.id.personSpecies);
        vehiclesOrStarshipsAdapter = new VehiclesOrStarshipsArrayAdapter(this, PersonController.vehicleOrStarshipArray);
        ListView vehiclesAndStarshipsListView = findViewById(R.id.vehiclesAndStarshipsList);
        vehiclesAndStarshipsListView.setAdapter(vehiclesOrStarshipsAdapter);
        vehiclesAndStarshipsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonController.showDialog(context, position);
            }
        });
        PersonController.generateSpeciesVehiclesAndStarshipsLists(person.speciesEndpoints,
                                                                  person.vehiclesEndpoints,
                                                                  person.starshipsEndpoints);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PeopleActivity.personClicked = false;
    }

    public static void setPersonSpecies(String species) {
        synchronized (context.species) {
            context.species.setText(species);
        }
    }

    public static void notifyVehicleOrShartshipChanged() {
        synchronized (context.vehiclesOrStarshipsAdapter) {
            context.vehiclesOrStarshipsAdapter.notifyDataSetChanged();
        }
    }
}
