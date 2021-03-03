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

public class ColorsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> colorList = new ArrayList<Word>();
        colorList.add(new Word("red","weṭeṭṭi"));
        colorList.add(new Word("green","chokokki"));
        colorList.add(new Word("brown","ṭakaakki"));
        colorList.add(new Word("gray","ṭopoppi"));
        colorList.add(new Word("black","kululli"));
        colorList.add(new Word("white","kelelli"));
        colorList.add(new Word("dusty yellow","ṭopiisә"));
        colorList.add(new Word("mustard yellow","chiwiiṭә"));

        WordAdapter wordAdapter = new WordAdapter(this,colorList);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

    }
}
