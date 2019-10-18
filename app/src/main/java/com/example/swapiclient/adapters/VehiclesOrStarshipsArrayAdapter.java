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
import com.example.swapiclient.models.Transport;
import java.util.ArrayList;
import java.util.List;

public class VehiclesOrStarshipsArrayAdapter extends ArrayAdapter<Transport> {

    private Context mContext;
    private List<Transport> vehicleOrStarships;

    public VehiclesOrStarshipsArrayAdapter(Context context, ArrayList<Transport> vehicleOrStarships) {
        super(context, 0, vehicleOrStarships);
        mContext = context;
        this.vehicleOrStarships = vehicleOrStarships;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View listItem = convertView != null ? convertView :
                LayoutInflater.from(mContext).inflate(R.layout.vehicle_or_starship,parent,false);
        Transport vehicleOrStarship = vehicleOrStarships.get(position);
        TextView name = listItem.findViewById(R.id.vehicleOrStarshipName);
        TextView type = listItem.findViewById(R.id.vehicleOrStarship);
        name.setText(vehicleOrStarship.name);
        type.setText(vehicleOrStarship.getType());
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
