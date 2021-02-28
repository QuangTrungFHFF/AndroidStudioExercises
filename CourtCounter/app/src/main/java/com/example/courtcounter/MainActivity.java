package com.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvScoreA, tvScoreB;
    Button teamAPlusThree, teamAPlusTwo,teamAFree;
    Button teamBPlusThree, teamBPlusTwo,teamBFree;
    Button btnReset;

    int scoreA =0;
    int scoreB =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTeamAScore(0);

        //Team A ----------------------------------------------------

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

        //Team B ----------------------------------------------------

        teamBPlusThree = (Button)findViewById(R.id.teamb_plus_three);
        teamBPlusTwo = (Button)findViewById(R.id.teamb_plus_two);
        teamBFree = (Button)findViewById(R.id.teamb_free);

        teamBPlusThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreB+=3;
                displayTeamBScore(scoreB);
            }
        });

        teamBPlusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreB+=2;
                displayTeamBScore(scoreB);
            }
        });

        teamBFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreB+=1;
                displayTeamBScore(scoreB);
            }
        });

        //Reset Button ----------------------------------------------------

        btnReset = (Button)findViewById(R.id.reset_score);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreA = 0;
                scoreB = 0;
                displayTeamAScore(scoreA);
                displayTeamBScore(scoreB);
                Toast toast = Toast.makeText(getApplicationContext(),"Scores are reseted",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }


    private void displayTeamAScore(int score)
    {
        tvScoreA = (TextView)findViewById(R.id.teama_score);
        tvScoreA.setText(String.valueOf(score));
    }

    private void displayTeamBScore(int score)
    {
        tvScoreB = (TextView)findViewById(R.id.teamb_score);
        tvScoreB.setText(String.valueOf(score));
    }
}