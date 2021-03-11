package com.example.earthquake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        holder.mMag.setText(getFormattedMag(earthquakeInfo.getMag()));
        holder.mLocation.setText(getFormattedLocationText(earthquakeInfo.getLocation()));
        holder.mCity.setText(getFormattedLocationCity(earthquakeInfo.getLocation()));
        holder.mDate.setText(getFormattedDate(earthquakeInfo.getDate()));
        holder.mTime.setText(getFormattedTime(earthquakeInfo.getDate()));

    }

    @Override
    public int getItemCount() {
        return earthquakeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mMag;
        public TextView mLocation;
        public TextView mCity;
        public TextView mDate;
        public TextView mTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mMag = (TextView)itemView.findViewById(R.id.mag_textview);
            mLocation = (TextView)itemView.findViewById(R.id.location_textview);
            mCity = (TextView)itemView.findViewById(R.id.city_textview);
            mDate = (TextView)itemView.findViewById(R.id.date_textview);
            mTime = (TextView)itemView.findViewById(R.id.time_textview);
        }
    }


    private String getFormattedDate(long time){
        String date="";
        Date dateObject = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM DD, yyyy");
        date = simpleDateFormat.format(dateObject);
        return date;
    }

    private String getFormattedTime(long time){
        String date="";
        Date dateObject = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        date = simpleDateFormat.format(dateObject);
        return date;
    }

    private String getFormattedLocationCity(String location){
        String result ="";
        String segments[] = location.split(" ");
        if(segments.length >1){
            result = segments[segments.length -2]+ " " + segments[segments.length-1];
        }
        else{
            result = location;
        }

        return result.trim();
    }

    private String getFormattedLocationText(String location){
        String result ="";
        String segments[] = location.split(" ");
        if(segments.length>2){
            for(int i =0; i<(segments.length-2);i++)
            {
                result += segments[i] + " ";
            }
        }
        else{
            result ="Near the";
        }

        return result.trim();
    }

    private String getFormattedMag(double mag){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String output = decimalFormat.format(mag);
        return output;
    }
}
