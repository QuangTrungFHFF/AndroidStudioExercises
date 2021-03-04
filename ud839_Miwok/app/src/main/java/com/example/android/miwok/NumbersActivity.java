/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private ArrayList<Word> englishNumbers = new ArrayList<Word>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener mMediaOnCompletetionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        englishNumbers.add(new Word("one","lutti",R.raw.number_one, R.drawable.number_one));
        englishNumbers.add(new Word("two","otiiko",R.raw.number_two, R.drawable.number_two));
        englishNumbers.add(new Word("three","tolookosu",R.raw.number_three, R.drawable.number_three));
        englishNumbers.add(new Word("four","oyyisa",R.raw.number_four, R.drawable.number_four));
        englishNumbers.add(new Word("five","massokka",R.raw.number_five, R.drawable.number_five));
        englishNumbers.add(new Word("six","temmokka",R.raw.number_six, R.drawable.number_six));
        englishNumbers.add(new Word("seven","kenekaku",R.raw.number_seven, R.drawable.number_seven));
        englishNumbers.add(new Word("eight","kawinta",R.raw.number_eight, R.drawable.number_eight));
        englishNumbers.add(new Word("nine","wo’e",R.raw.number_nine, R.drawable.number_nine));
        englishNumbers.add(new Word("ten","na’aacha",R.raw.number_ten, R.drawable.number_ten));

        ListView listView = (ListView)findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(this, englishNumbers, R.color.category_numbers);
        // Get a reference to the ListView, and attach the adapter to the listView.
        //ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                int audioId = englishNumbers.get(position).getAudioResourceId();
                    if(audioId>=0) {
                        releaseMediaPlayer();
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), audioId);
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(mMediaOnCompletetionListener);
                    }
            }
        });

    }

    private void releaseMediaPlayer(){
        if(mediaPlayer!= null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }


//    private int getAudioIdFromName(String songname)
//    {
//        int resId =0;
//        resId = getResources().getIdentifier(songname,"raw",getPackageName());
//        return resId;
//    }

}
