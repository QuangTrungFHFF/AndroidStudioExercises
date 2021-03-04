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

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
            {
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };

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

        //
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> colorList = new ArrayList<Word>();
        colorList.add(new Word("red","weṭeṭṭi",R.raw.color_red, R.drawable.color_red));
        colorList.add(new Word("green","chokokki",R.raw.color_green, R.drawable.color_green));
        colorList.add(new Word("brown","ṭakaakki",R.raw.color_brown, R.drawable.color_brown));
        colorList.add(new Word("gray","ṭopoppi",R.raw.color_gray, R.drawable.color_gray));
        colorList.add(new Word("black","kululli",R.raw.color_black, R.drawable.color_black));
        colorList.add(new Word("white","kelelli",R.raw.color_white, R.drawable.color_white));
        colorList.add(new Word("dusty yellow","ṭopiisә",R.raw.color_dusty_yellow, R.drawable.color_dusty_yellow));
        colorList.add(new Word("mustard yellow","chiwiiṭә",R.raw.color_mustard_yellow, R.drawable.color_mustard_yellow));

        WordAdapter wordAdapter = new WordAdapter(this,colorList,R.color.category_colors);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int audioId = colorList.get(position).getAudioResourceId();
                if(audioId>=0)
                {
                    releaseMediaPlayer();

                    int requestFocus = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if(requestFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    {
                        mediaPlayer = MediaPlayer.create(ColorsActivity.this,audioId);
                        mediaPlayer.start();

                        mediaPlayer.setOnCompletionListener(mMediaOnCompletetionListener);
                    }
                }
            }
        });

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

    private void releaseMediaPlayer(){
        if(mediaPlayer!= null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

}
