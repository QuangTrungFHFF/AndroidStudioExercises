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

public class FamilyActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> familyList = new ArrayList<Word>();
        familyList.add(new Word("father","әpә",R.raw.family_father, R.drawable.family_father));
        familyList.add(new Word("mother","әṭa",R.raw.family_mother, R.drawable.family_mother));
        familyList.add(new Word("son","angsi",R.raw.family_son, R.drawable.family_son));
        familyList.add(new Word("daughter","tune",R.raw.family_daughter, R.drawable.family_daughter));
        familyList.add(new Word("older brother","taachi",R.raw.family_older_brother, R.drawable.family_older_brother));
        familyList.add(new Word("younger brother","chalitti",R.raw.family_younger_brother, R.drawable.family_younger_brother));
        familyList.add(new Word("older sister","teṭe",R.raw.family_older_sister, R.drawable.family_older_sister));
        familyList.add(new Word("younger sister","kolliti",R.raw.family_younger_sister, R.drawable.family_younger_sister));
        familyList.add(new Word("grandmother","ama",R.raw.family_grandmother, R.drawable.family_grandmother));
        familyList.add(new Word("grandfather","paapa",R.raw.family_grandfather, R.drawable.family_grandfather));

        WordAdapter wordAdapter = new WordAdapter(this,familyList, R.color.category_family);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int audioId = familyList.get(position).getAudioResourceId();
                if(audioId>=0)
                {
                    mediaPlayer = MediaPlayer.create(FamilyActivity.this,audioId);
                    mediaPlayer.start();
                }

            }
        });
    }
}
