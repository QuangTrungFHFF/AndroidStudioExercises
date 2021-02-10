package com.example.helloworld;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null)
        {
            Log.e(TAG, "BUG Quang Trung");
        }
        else
        {
            Log.e(TAG, "Welcome to my first android project");
        }

        setContentView(R.layout.activity_main);
        Log.d("Main activity","Hello World");

        button = new Button(this);
        button.setText("Free Education");
    }
}