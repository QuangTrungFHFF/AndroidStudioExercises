package com.example.earthquake;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private QueryUtils() {
    }



    public static ArrayList<EarthquakeInfo> extractEarthquakes(String mUrl) {

        String jsonString;
        URL url = createURL(mUrl);
        jsonString = makeHttpRequest(url);

        if (jsonString == null) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakeInfo> earthquakes = new ArrayList<>();
        Log.e("Taf","Here <-------------------------------------------------");

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
//
            JSONObject root = new JSONObject(jsonString);
            JSONArray feature = root.optJSONArray("features");
            for(int i = 0; i<feature.length();i++){
                JSONObject earthquake = feature.optJSONObject(i);
                JSONObject properties = earthquake.optJSONObject("properties");

                double mag = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String Url = properties.getString("url");
                earthquakes.add(new EarthquakeInfo(mag,location,time, Url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    /**
     * Generate URL object from given String
     */
    private static URL createURL(String stringUrl){
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

    private static String makeHttpRequest(URL url){
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

    private static String readInputStream(InputStream inputStream) throws IOException {
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
}
