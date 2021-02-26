package com.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvScoreA;
    Button teamAPlusThree, teamAPlusTwo,teamAFree;
    int scoreA =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTeamAScore(0);

        teamAPlusThree = (Button)findViewById(R.id.teama_plus_three);
        teamAPlusTwo = (Button)findViewById(R.id.teama_plus_two);
        teamAFree = (Button)findViewById(R.id.teama_free);

        teamAPlusThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreA+=3;
                displayTeamAScore(scoreA);
            }
        });

        teamAPlusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreA+=2;
                displayTeamAScore(scoreA);
            }
        });

        teamAFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreA+=1;
                displayTeamAScore(scoreA);
            }
        });
    }


    private void displayTeamAScore(int score)
    {
        tvScoreA = (TextView)findViewById(R.id.teama_score);
        tvScoreA.setText(String.valueOf(score));
    }
}