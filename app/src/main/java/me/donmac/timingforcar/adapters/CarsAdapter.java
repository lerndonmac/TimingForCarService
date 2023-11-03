package me.donmac.timingforcar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import me.donmac.timingforcar.R;
import me.donmac.timingforcar.model.Car;

public class CarsAdapter extends ArrayAdapter<Car> {
    public CarsAdapter(@NonNull Context context, @NonNull List<Car> objects) {
        super(context, 0, objects);
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car client = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.actual_cars_list_item, parent, false);
        if (convertView != null) {
            TextView tvMan = convertView.findViewById(R.id.tvFirst);
            TextView tvMod = convertView.findViewById(R.id.tvSecond);
            TextView tvYear = convertView.findViewById(R.id.tvTherd);
            if (client.getModel() != null) {
                tvMan.setText(client.getManufacturer());
                tvMod.setText(client.getModel());
                tvYear.setText(String.valueOf(client.getYear()));
            }
        }
        return convertView;
    }
}
