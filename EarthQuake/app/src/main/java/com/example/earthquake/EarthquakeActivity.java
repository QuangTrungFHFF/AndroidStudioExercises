package com.example.earthquake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements EarthquakeAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<ArrayList<EarthquakeInfo>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String EARTHQUAKE_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=3&limit=25";

    private EarthquakeAdapter mAdapter;
    TextView emptyView;
    RecyclerView rvEarthquake;

    ArrayList<EarthquakeInfo> earthquakeInfoArrayList = new ArrayList<EarthquakeInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        //earthquakeInfoArrayList = QueryUtils.extractEarthquakes();

        /**HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute(EARTHQUAKE_REQUEST_URL);**/
        rvEarthquake = (RecyclerView)findViewById(R.id.earthquake_rv);
        emptyView = (TextView)findViewById(R.id.empty_view);

        mAdapter = new EarthquakeAdapter(this,earthquakeInfoArrayList,this);
        rvEarthquake.setAdapter(mAdapter);

        rvEarthquake.setLayoutManager(new LinearLayoutManager(this));

        //getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);


    }

    private void updateUI(ArrayList<EarthquakeInfo> earthquakeInfos){
        earthquakeInfoArrayList = earthquakeInfos;

        if(earthquakeInfoArrayList.size() ==0){
            emptyView.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyView.setVisibility(View.GONE);
        }

        //RecyclerView rvEarthquake = (RecyclerView)findViewById(R.id.earthquake_rv);

        mAdapter = new EarthquakeAdapter(this,earthquakeInfoArrayList,this);
        rvEarthquake.setAdapter(mAdapter);
        rvEarthquake.setLayoutManager(new LinearLayoutManager(this));


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



    @NonNull
    @Override
    public Loader<ArrayList<EarthquakeInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(this,EARTHQUAKE_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<EarthquakeInfo>> loader, ArrayList<EarthquakeInfo> data) {
        updateUI(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<EarthquakeInfo>> loader) {
        mAdapter.clear();
    }



    /**
     * AsyncTask
     */

    private class HttpRequestTask extends AsyncTask<String,Void,ArrayList<EarthquakeInfo>>{

        @Override
        protected ArrayList<EarthquakeInfo> doInBackground(String... urls) {
            if(urls.length<1 || urls[0]== null){
                return null;
            }
            ArrayList<EarthquakeInfo> earthquakes;
            earthquakes = QueryUtils.extractEarthquakes(urls[0]);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<EarthquakeInfo> earthquakeInfos) {
            if(earthquakeInfos!=null){
                updateUI(earthquakeInfos);
            }
            else
            {
                Log.e(LOG_TAG, "Error with update UI");
            }
        }
    }



}