package com.example.earthquake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements EarthquakeAdapter.OnItemClickListener {

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final String EARTHQUAKE_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    ArrayList<EarthquakeInfo> earthquakeInfoArrayList = new ArrayList<EarthquakeInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


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

    /**
     * Generate URL object from given String
     */
    private URL createURL(String stringUrl){
         URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }
        return url;
    }

    /**
     * Make Http request
     */

    private String makeHttpRequest(URL url){
        String JSONresponse = "";

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == 200){
                inputStream =httpURLConnection.getInputStream();
                JSONresponse = readInputStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSONresponse;
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = br.readLine();
            while(line!= null){
                output.append(line);
                line = br.readLine();
            }
        }
        return output.toString();
    }
    /**
     * AsyncTask
     */

    private class httpRequestTask extends AsyncTask<URL,Void,ArrayList<EarthquakeInfo>{

        @Override
        protected ArrayList<EarthquakeInfo> doInBackground(URL... urls) {
            return null;
        }
    }

}