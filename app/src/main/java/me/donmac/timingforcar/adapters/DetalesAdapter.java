package me.donmac.timingforcar.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import me.donmac.timingforcar.R;
import me.donmac.timingforcar.model.ServiceKit;

public class DetalesAdapter extends ArrayAdapter<ServiceKit> {
    public DetalesAdapter(@NonNull Context context, @NonNull List<ServiceKit> objects) {
        super(context, 0, objects);
    }
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ServiceKit serviceKit = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.actual_cars_list_item, parent, false);
        if (convertView != null){
            TextView tvName = convertView.findViewById(R.id.tvFirst);
            TextView tvProbeegS = convertView.findViewById(R.id.tvSecond);
            TextView tvProbegE = convertView.findViewById(R.id.tvTherd);
            TextView tvDopRow = convertView.findViewById(R.id.tvDopRow);
            if (serviceKit.getName() != null){
                tvName.setText(serviceKit.getName());
                tvProbeegS.setText(serviceKit.getProbeg() +" KM");
                tvProbegE.setText(serviceKit.getZamena() +" KM");
                tvDopRow.setVisibility(View.VISIBLE);
                if (serviceKit.getCost()==0)
                    tvDopRow.setText("");
                else{
                    tvDopRow.setText(serviceKit.getCost()+"p");
                }
            }
        }
        return convertView;
    }
}
