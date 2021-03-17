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
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.android.pets.data.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.example.android.pets.data.PetContract;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPets();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertPets() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PetContract.PetsEntry.COLUMN_NAME, "Toboki");
        values.put(PetContract.PetsEntry.COLUMN_BREED, "Ack ack");
        values.put(PetContract.PetsEntry.COLUMN_GENDER, PetContract.PetsEntry.GENDER_MALE);

        long rowId = db.insert(PetContract.PetsEntry.TABLE_NAME,null,values);
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        PetDbHelper mDbHelper = new PetDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        String[] project = {
            PetContract.PetsEntry._ID,
            PetContract.PetsEntry.COLUMN_NAME
            ,PetContract.PetsEntry.COLUMN_BREED
            , PetContract.PetsEntry.COLUMN_GENDER
            , PetContract.PetsEntry.COLUMN_WEIGHT
            };

        Cursor cursor = db.query(PetContract.PetsEntry.TABLE_NAME,project,null,null,null,null,null);
        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            displayView.setText("Number of rows in pets database table: " + cursor.getCount() + "\n\n");

            int idColumnIndex = cursor.getColumnIndex(PetContract.PetsEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_WEIGHT);

            displayView.append(PetContract.PetsEntry._ID + " - " + PetContract.PetsEntry.COLUMN_NAME + " - " + PetContract.PetsEntry.COLUMN_BREED
            + " - " + PetContract.PetsEntry.COLUMN_GENDER + " - " + PetContract.PetsEntry.COLUMN_WEIGHT + "\n");

            while(cursor.moveToNext()){
                int mId = cursor.getInt(idColumnIndex);
                String mName = cursor.getString(nameColumnIndex);
                String mBreed = cursor.getString(breedColumnIndex);
                int mGenderInt = cursor.getInt(genderColumnIndex);
                String mGender = getGenderString(mGenderInt);
                int mWeight = cursor.getInt(weightColumnIndex);

                displayView.append(mId + " - " +
                        mName + " - " +
                        mBreed + " - " +
                        mGender + " - " +
                        mWeight + "\n");
            }


        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private String getGenderString(int i){
        String result = "Unknown";
        if(i == 1){
            result = "MALE";
        }
        else if (i == 2){
            result = "FEMALE";
        }
        return result;

    }
}
