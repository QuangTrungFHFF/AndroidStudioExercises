package com.example.tourguideapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PostCardsActivity extends AppCompatActivity {

    private ArrayList<PostCard> postCardArrayList = new ArrayList<PostCard>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postcards_list);

        PostCard postCard = new PostCard(R.drawable.frankfurt,"Frankfurt","Bridge",10);
        postCardArrayList.add(new PostCard(R.drawable.frankfurt,"Frankfurt","Bridge",10));

        ListView listView = (ListView)findViewById(R.id.postcards_listview);

        CustomArrayAdapter customArrayAdapter = new CustomArrayAdapter(this,postCardArrayList);

        listView.setAdapter(customArrayAdapter);



    }
}
