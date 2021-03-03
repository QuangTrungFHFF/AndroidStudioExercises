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
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> familyList = new ArrayList<Word>();
        familyList.add(new Word("father","әpә"));
        familyList.add(new Word("mother","әṭa"));
        familyList.add(new Word("son","angsi"));
        familyList.add(new Word("daughter","tune"));
        familyList.add(new Word("older brother","taachi"));
        familyList.add(new Word("younger brother","chalitti"));
        familyList.add(new Word("older sister","teṭe"));
        familyList.add(new Word("younger sister","kolliti"));
        familyList.add(new Word("grandmother","ama"));
        familyList.add(new Word("grandfather","paapa"));

        WordAdapter wordAdapter = new WordAdapter(this,familyList);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(wordAdapter);
    }
}
