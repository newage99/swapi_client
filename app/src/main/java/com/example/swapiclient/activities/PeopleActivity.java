package com.example.swapiclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.swapiclient.R;
import com.example.swapiclient.adapters.PeopleArrayAdapter;
import com.example.swapiclient.controllers.PeopleController;

public class PeopleActivity extends AppCompatActivity {

    private static PeopleActivity context;
    private PeopleArrayAdapter peopleAdapter;
    public static boolean personClicked = false; // Variable to avoid clicking two people'd make the Activities to create.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_people);
        peopleAdapter = new PeopleArrayAdapter(this, PeopleController.peopleArray);
        ListView peopleListView = findViewById(R.id.personsList);
        peopleListView.setAdapter(peopleAdapter);
        peopleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!personClicked) {
                    personClicked = true;
                    Intent intent = new Intent(context, PersonActivity.class);
                    intent.putExtra("Person", PeopleController.peopleArray.get(position));
                    startActivity(intent);
                }
            }
        });
        PeopleController.generatePeopleList();
    }

    public static void notifyPeopleChanged() {
        synchronized (context.peopleAdapter) {
            context.peopleAdapter.notifyDataSetChanged();
        }
    }
}
