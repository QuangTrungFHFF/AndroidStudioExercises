package com.example.secondproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String STATE = "Trạng thái";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(STATE, "onCreate");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(STATE, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(STATE, "onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(STATE, "onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(STATE, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(STATE, "onDestroy");
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(STATE, "onRestoreInstanceState");
    }



    @Override
    public void onRestart() {
        super.onRestart();
        Log.e(STATE, "onRestart");
    }
}