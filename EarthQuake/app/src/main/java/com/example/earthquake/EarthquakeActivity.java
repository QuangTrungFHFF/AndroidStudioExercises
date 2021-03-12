package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements EarthquakeAdapter.OnItemClickListener {

    ArrayList<EarthquakeInfo> earthquakeInfoArrayList = new ArrayList<EarthquakeInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //earthquakeInfoArrayList = createEarthquakeList();
        earthquakeInfoArrayList = QueryUtils.extractEarthquakes();
        RecyclerView rvEarthquake = (RecyclerView)findViewById(R.id.earthquake_rv);

        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this,earthquakeInfoArrayList,this);
        rvEarthquake.setAdapter(earthquakeAdapter);
        rvEarthquake.setLayoutManager(new LinearLayoutManager(this));

    }



    private ArrayList<EarthquakeInfo> createEarthquakeList(){
        ArrayList<EarthquakeInfo> earthquakeInfoArrayList = new ArrayList<EarthquakeInfo>();

        earthquakeInfoArrayList.add(new EarthquakeInfo(5.5,"Tokyo",145625478, "none"));
        earthquakeInfoArrayList.add(new EarthquakeInfo(7.2,"Ha Noi",4525478, "none"));
        earthquakeInfoArrayList.add(new EarthquakeInfo(4.5,"Berlin",1665625478, "none"));
        earthquakeInfoArrayList.add(new EarthquakeInfo(5.6,"Paris",143123478, "none"));
        earthquakeInfoArrayList.add(new EarthquakeInfo(5.8,"London",1433135478, "none"));

        return earthquakeInfoArrayList;
    }


    @Override
    public void onItemClick( int position) {
        EarthquakeInfo earthquake = earthquakeInfoArrayList.get(position);
        String text = earthquake.toString();
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();

        String mUrl = earthquake.getmUrl();
        openWebpage(mUrl);

    }

    private void openWebpage(String uri){
        Uri webpage = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }
}