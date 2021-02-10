package com.example.listviewexample;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] items = {"Quang Trung", "Bich Ngoc", "Thanh Xuan"};

        listView = (ListView)findViewById(R.id.list_view);

        ListViewAdapter adapter = new ListViewAdapter(this,items);
        listView.setAdapter(adapter);
    }
}