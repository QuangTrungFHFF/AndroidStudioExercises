package com.example.earthquake;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeInfo>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    String mUrl;

    public EarthquakeLoader(@NonNull Context context,String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<EarthquakeInfo> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<EarthquakeInfo> earthquakes = QueryUtils.extractEarthquakes(mUrl);
        return earthquakes;
    }

}
