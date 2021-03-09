package com.example.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiating ViewPager
        ViewPager viewPager = (ViewPager)findViewById(R.id.main_viewpager);
        CustomFragmentPagerAdapter customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customFragmentPagerAdapter);

        jump = (Button)findViewById(R.id.to_list_button);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PostCardsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}