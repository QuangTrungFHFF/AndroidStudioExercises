package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private Button start, stop, sent,sentback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        start = (Button)findViewById(R.id.startServiceButton);
        stop = (Button)findViewById(R.id.stopServiceButton);
        sent = (Button)findViewById(R.id.sentExplicitIntentButton);
        sentback = (Button)findViewById(R.id.sentImplicitIntentButton);

        if(getIntent().getExtras()!= null  )
        {
            Bundle extras = getIntent().getExtras();
            String data = extras.getString("Value1");
            Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
        }


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(SecondActivity.this,ServiceExample.class));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(SecondActivity.this,ServiceExample.class));
            }
        });

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));

                startActivity(intent);
            }
        });

        sentback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("returnKey1","Return Value = 1");
        data.putExtra("returnKey2", "Return Value = 2");
        setResult(RESULT_OK,data);
        super.finish();
    }
}