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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        final ArrayList<Word> phraseList = new ArrayList<Word>();
        phraseList.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        phraseList.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phraseList.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phraseList.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phraseList.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phraseList.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        phraseList.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phraseList.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phraseList.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        phraseList.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        WordAdapter wordAdapter = new WordAdapter(this,phraseList,R.color.category_phrases);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int audioId = phraseList.get(position).getAudioResourceId();
                if(audioId>=0){
                    mediaPlayer =MediaPlayer.create(PhrasesActivity.this,audioId);
                    mediaPlayer.start();
                }
            }
        });
    }
}
