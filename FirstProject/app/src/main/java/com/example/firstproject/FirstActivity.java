package com.example.firstproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button testButton = (Button)findViewById(R.id.btnClickMe);
        Button sendToTwoButton = (Button)findViewById(R.id.sentIntentToTwo);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);

                startActivity(intent);
                Log.e("Tai test Button ","Da sent intent");
            }
        });

        sendToTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getValueIntent = new Intent(FirstActivity.this, SecondActivity.class);
                getValueIntent.putExtra("Value1", "This value one for Second Activity");
                getValueIntent.putExtra("Value2", "This value two for Second Activity");

                int REQUEST_CODE = 9;
                startActivityForResult(getValueIntent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 9)
        {
            if(data.hasExtra("returnKey1"))
            {
                Toast.makeText(this, data.getStringExtra("returnKey1"), Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}