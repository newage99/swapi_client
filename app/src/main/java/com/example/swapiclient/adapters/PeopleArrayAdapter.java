package com.example.swapiclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.swapiclient.R;
import com.example.swapiclient.models.Person;
import java.util.ArrayList;
import java.util.List;

public class PeopleArrayAdapter extends ArrayAdapter<Person> {

    private Context mContext;
    private List<Person> persons;

    public PeopleArrayAdapter(Context context, ArrayList<Person> persons) {
        super(context, 0, persons);
        mContext = context;
        this.persons = persons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View listItem = convertView != null ? convertView :
                LayoutInflater.from(mContext).inflate(R.layout.person,parent,false);
        Person currentPerson = persons.get(position);
        TextView name = listItem.findViewById(R.id.personNameTextView);
        TextView gender = listItem.findViewById(R.id.personGenderTextView);
        TextView natalPlanet = listItem.findViewById(R.id.personNatalPlanetTextView);
        name.setText(currentPerson.name);
        gender.setText(currentPerson.gender);
        natalPlanet.setText(currentPerson.homeWorld);
        return listItem;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}