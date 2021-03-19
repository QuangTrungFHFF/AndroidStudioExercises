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

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.example.android.pets.data.PetCursorAdapter;
import com.example.android.pets.data.PetDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.android.pets.data.PetContract;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PET_LOADER = 0;

    PetCursorAdapter sCursorAdapter;

    private PetDbHelper mDbHelper;
    private ListView listView;
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
        listView = (ListView)findViewById(R.id.list_view_pets);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        sCursorAdapter = new PetCursorAdapter(this,null);
        listView.setAdapter(sCursorAdapter);

        LoaderManager.getInstance(this).initLoader(PET_LOADER,null,this);        

        mDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

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
        Uri uri;

        ContentValues values = new ContentValues();
        values.put(PetContract.PetsEntry.COLUMN_NAME, "Toboki");
        values.put(PetContract.PetsEntry.COLUMN_BREED, "Ack ack");
        values.put(PetContract.PetsEntry.COLUMN_GENDER, PetContract.PetsEntry.GENDER_MALE);

        uri = getContentResolver().insert(PetContract.PetsEntry.CONTENT_URI,values);

        long rowId = ContentUris.parseId(uri);


    }

//    private void displayDatabaseInfo() {
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//        PetDbHelper mDbHelper = new PetDbHelper(this);
//
//        // Create and/or open a database to read from it
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();
//
//        // Perform this raw SQL query "SELECT * FROM pets"
//        // to get a Cursor that contains all rows from the pets table.
//        String[] project = {
//            PetContract.PetsEntry._ID,
//            PetContract.PetsEntry.COLUMN_NAME
//            ,PetContract.PetsEntry.COLUMN_BREED
//            , PetContract.PetsEntry.COLUMN_GENDER
//            , PetContract.PetsEntry.COLUMN_WEIGHT
//            };
//
//        //Cursor cursor = db.query(PetContract.PetsEntry.TABLE_NAME,project,null,null,null,null,null);
//        Cursor cursor = getContentResolver().query(PetContract.PetsEntry.CONTENT_URI,project,null,null,null);
//
//        PetCursorAdapter petCursorAdapter = new PetCursorAdapter(this,cursor);
//        listView.setAdapter(petCursorAdapter);
//
//    }

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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                PetContract.PetsEntry._ID,
                PetContract.PetsEntry.COLUMN_NAME
                ,PetContract.PetsEntry.COLUMN_BREED
        };
        return new CursorLoader(this, PetContract.PetsEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        sCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        sCursorAdapter.swapCursor(null);
    }
}
