package com.example.earthquake;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.ViewHolder> {

    private List<EarthquakeInfo> earthquakeInfoList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public EarthquakeAdapter(Context context,List<EarthquakeInfo> earthquakeInfoList,OnItemClickListener mOnItemClickListener) {
        this.earthquakeInfoList = earthquakeInfoList;
        this.context = context;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.earthquake_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EarthquakeInfo earthquakeInfo = earthquakeInfoList.get(position);

        holder.mMag.setText(getFormattedMag(earthquakeInfo.getMag()));
        GradientDrawable gradientDrawable = (GradientDrawable)holder.mMag.getBackground();
        gradientDrawable.setColor(getMagnitudeColor(earthquakeInfo.getMag()));


        holder.mLocation.setText(getFormattedLocationText(earthquakeInfo.getLocation()));
        holder.mCity.setText(getFormattedLocationCity(earthquakeInfo.getLocation()));
        holder.mDate.setText(getFormattedDate(earthquakeInfo.getDate()));
        holder.mTime.setText(getFormattedTime(earthquakeInfo.getDate()));

    }


    @Override
    public int getItemCount() {
        return earthquakeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mMag;
        public TextView mLocation;
        public TextView mCity;
        public TextView mDate;
        public TextView mTime;
        OnItemClickListener onItemClickListener;


        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mMag = (TextView)itemView.findViewById(R.id.mag_textview);
            mLocation = (TextView)itemView.findViewById(R.id.location_textview);
            mCity = (TextView)itemView.findViewById(R.id.city_textview);
            mDate = (TextView)itemView.findViewById(R.id.date_textview);
            mTime = (TextView)itemView.findViewById(R.id.time_textview);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

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
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String output = decimalFormat.format(mag);
        return output;
    }

    private int getMagnitudeColor(double mag)
    {
        int magnitudeResourceId;
        int magnitudeFloor = (int)Math.floor(mag);
        Log.e("color",String.valueOf(magnitudeFloor));
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeResourceId =R.color.magnitude10plus;
                break;
        }
        return context.getColor(magnitudeResourceId);
    }

    public void clear() {
        int size = earthquakeInfoList.size();
        earthquakeInfoList.clear();
        notifyItemRangeRemoved(0, size);
    }
}
