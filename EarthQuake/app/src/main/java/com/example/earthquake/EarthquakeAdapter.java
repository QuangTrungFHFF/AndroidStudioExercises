package com.example.earthquake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {

    private List<EarthquakeInfo> earthquakeInfoList;

    public EarthquakeAdapter(List<EarthquakeInfo> earthquakeInfoList) {
        this.earthquakeInfoList = earthquakeInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.earthquake_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EarthquakeInfo earthquakeInfo = earthquakeInfoList.get(position);
        holder.mMag.setText(String.valueOf(earthquakeInfo.getMag()));
        holder.mLocation.setText(earthquakeInfo.getLocation());
        holder.mDate.setText(String.valueOf(earthquakeInfo.getDate()));

    }

    @Override
    public int getItemCount() {
        return earthquakeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mMag;
        public TextView mLocation;
        public TextView mDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMag = (TextView)itemView.findViewById(R.id.mag_textview);
            mLocation = (TextView)itemView.findViewById(R.id.location_textview);
            mDate = (TextView)itemView.findViewById(R.id.date_textview);
        }
    }
}
