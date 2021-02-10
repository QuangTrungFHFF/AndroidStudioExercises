package com.example.firstproject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.Provider;

public class ServiceExample extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this,"Service started!",Toast.LENGTH_SHORT).show();
        Log.e("Quang Trung", "Service da dc khoi tao");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this,"Service stopped!",Toast.LENGTH_SHORT).show();
        Log.e("Quang Trung", "Service da dc huy");
    }
}
