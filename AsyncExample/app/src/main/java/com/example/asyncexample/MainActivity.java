package com.example.asyncexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tvIP;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIP = (TextView) findViewById(R.id.tv_ip);
        Log.e("Done async","Tai day 1");
        new AsyncTaskNetwork(this, tvIP).execute("https://google.com/");
        Log.e("Done async","Tai day");
        tvIP.setText("asb");
    }

    private void testNetwork() {
        try {
            StringBuilder sb;
            // Website này sẽ cho biết địa chỉ IP của bạn.
            URL url = new URL("http://icanhazip.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                tvIP.setText(sb.toString());
                Log.e("Dia chi IP ", sb.toString());

                br.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

