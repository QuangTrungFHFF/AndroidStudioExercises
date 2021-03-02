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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    ArrayList<Word> englishNumbers = new ArrayList<Word>();
    LinearLayout rootView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        englishNumbers.add(new Word("one","lutti"));
        englishNumbers.add(new Word("two","otiiko"));
        englishNumbers.add(new Word("three","tolookosu"));
        englishNumbers.add(new Word("four","oyyisa"));
        englishNumbers.add(new Word("five","massokka"));
        englishNumbers.add(new Word("six","temmokka"));
        englishNumbers.add(new Word("seven","kenekaku"));
        englishNumbers.add(new Word("eight","kawinta"));
        englishNumbers.add(new Word("nine","wo’e"));
        englishNumbers.add(new Word("ten","na’aacha"));



        WordAdapter wordAdapter = new WordAdapter(this, englishNumbers);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);



    }
}
