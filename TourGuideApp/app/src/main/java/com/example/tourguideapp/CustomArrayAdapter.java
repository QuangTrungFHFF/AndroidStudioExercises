package com.example.tourguideapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<PostCard> {

    public CustomArrayAdapter(@NonNull Context context, ArrayList<PostCard> postcards) {
        super(context, 0, postcards );
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostCard postCard = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.postcard_item,parent,false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.postcard_image_view);
        TextView cityName = (TextView)convertView.findViewById(R.id.city_name);
        TextView landscapeName = (TextView)convertView.findViewById(R.id.landscape_name);
        TextView price = (TextView)convertView.findViewById(R.id.price);

        imageView.setImageResource(postCard.getmImageId());
        cityName.setText(postCard.getmCityName());
        landscapeName.setText(postCard.getmLandscapeName());
        price.setText(String.valueOf(postCard.getmPrice()));

        return convertView;
    }
}
