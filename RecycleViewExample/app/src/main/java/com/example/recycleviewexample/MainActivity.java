package com.example.recycleviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Person> people = new ArrayList<>();
        people.add(new Person("Long", true));
        people.add(new Person("My", false));
        people.add(new Person("Duong", true));
        people.add(new Person("Duyen", false));

        rvItems = (RecyclerView)findViewById(R.id.rv_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvItems.setLayoutManager(layoutManager);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(new RecyclerDataAdapter(people,this));

    }
}