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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetCursorAdapter;
import com.example.android.pets.data.PetDbHelper;

import java.net.URI;


/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** EditText field to enter the pet's name */
    private EditText mNameEditText;

    /** EditText field to enter the pet's breed */
    private EditText mBreedEditText;

    /** EditText field to enter the pet's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mGenderSpinner;

    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = PetContract.PetsEntry.GENDER_UNKNOWN;

    private static final int PET_LOADER = 0;

    private Uri mCurrentPetUri;

    private static final int INSERT_MODE = 0;

    private static final int UPDATE_MODE = 1;

    private int mode;

    private boolean mChange = false;

    PetCursorAdapter sCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        mNameEditText.setOnTouchListener(mOnTouchListener);
        mBreedEditText.setOnTouchListener(mOnTouchListener);
        mWeightEditText.setOnTouchListener(mOnTouchListener);
        mGenderSpinner.setOnTouchListener(mOnTouchListener);

        setupSpinner();

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if(uri != null){
            mode = UPDATE_MODE;
            setTitle("Edit Pet");
            Toast.makeText(this,uri.toString(),Toast.LENGTH_SHORT).show();
            mCurrentPetUri = uri;
            LoaderManager.getInstance(this).initLoader(PET_LOADER,null,this);
        }
        else
        {
            mode = INSERT_MODE;
        }
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetContract.PetsEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetContract.PetsEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetContract.PetsEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                savePet();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                if(!mChange){
                    // Navigate back to parent activity (CatalogActivity)
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    }
                };
                showUnsavedChangesDialog(discardButtonListener);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreparePanel(int featureId, @Nullable View view, @NonNull Menu menu) {
        super.onPreparePanel(featureId, view, menu);
        if(mCurrentPetUri == null){
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    private void savePet() {

        String mName="";
        mName = mNameEditText.getText().toString();
        String mBreed = "";
        mBreed = mBreedEditText.getText().toString();
        int mWeight = 0;
        try {
            mWeight = Integer.parseInt(mWeightEditText.getText().toString());
            if (mWeight <0){
                mWeight = 0;
                Toast.makeText(this,"Cannot recognize weight value. Weight is now set to 0!",Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Cannot recognize weight value. Weight is now set to 0!",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(mName)|| TextUtils.isEmpty(mBreed)){
            Toast.makeText(this,"Please input your pet information (name and breed)!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(mode == INSERT_MODE){
                long mRowId = addToDataBase(mName,mBreed,mWeight);
                Toast.makeText(this,"Added to " + mRowId,Toast.LENGTH_SHORT).show();
            }
            else {
                int mUpdate = updateToDataBase(mName,mBreed,mWeight);
                Toast.makeText(this,"Update to " + mUpdate,Toast.LENGTH_SHORT).show();
            }

        }

    }

    private long addToDataBase(String mName,String mBreed, int mWeight){
        PetDbHelper mDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri uri;

        ContentValues contentValues = new ContentValues();
        contentValues.put(PetContract.PetsEntry.COLUMN_NAME,mName);
        contentValues.put(PetContract.PetsEntry.COLUMN_BREED,mBreed);
        contentValues.put(PetContract.PetsEntry.COLUMN_GENDER,mGender);
        contentValues.put(PetContract.PetsEntry.COLUMN_WEIGHT,mWeight);

        uri = getContentResolver().insert(PetContract.PetsEntry.CONTENT_URI,contentValues);

        long mRowId = ContentUris.parseId(uri);
        return mRowId;
    }

    private int updateToDataBase(String mName,String mBreed,int mWeight){
        PetDbHelper mDbHelper = new PetDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PetContract.PetsEntry.COLUMN_NAME,mName);
        contentValues.put(PetContract.PetsEntry.COLUMN_BREED,mBreed);
        contentValues.put(PetContract.PetsEntry.COLUMN_GENDER,mGender);
        contentValues.put(PetContract.PetsEntry.COLUMN_WEIGHT,mWeight);

        int update = getContentResolver().update(mCurrentPetUri,contentValues,null,null);

        return update;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {PetContract.PetsEntry._ID
                ,PetContract.PetsEntry.COLUMN_NAME
                , PetContract.PetsEntry.COLUMN_BREED
                , PetContract.PetsEntry.COLUMN_GENDER
                , PetContract.PetsEntry.COLUMN_BREED
                , PetContract.PetsEntry.COLUMN_WEIGHT};
        return new CursorLoader(this,mCurrentPetUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if(cursor.moveToFirst()){

            int indexId = cursor.getColumnIndex(PetContract.PetsEntry._ID);
            int indexName = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_NAME);
            int indexBreed = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_BREED);
            int indexGender = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_GENDER);
            int indexWeight = cursor.getColumnIndex(PetContract.PetsEntry.COLUMN_WEIGHT);

            String mName = cursor.getString(indexName);
            String mBreed = cursor.getString(indexBreed);
            mGender = cursor.getInt(indexGender);
            int mWeight = cursor.getInt(indexWeight);

            updateView(mName,mBreed,mGender,mWeight);
        }

    }

    private void updateView(String mName,String mBreed, int mGender,int mWeight  ) {
        int mSpinnerGender = getGender(mGender);

        mNameEditText.setText(mName);
        mBreedEditText.setText(mBreed);
        mGenderSpinner.setSelection(mSpinnerGender);
        mWeightEditText.setText(String.valueOf(mWeight));
    }

    private int getGender(int mGender) {
        switch (mGender) {
            case PetContract.PetsEntry.GENDER_MALE:
                return  1;
            case PetContract.PetsEntry.GENDER_FEMALE:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        //sCursorAdapter.swapCursor(null);
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mChange = true;
            return false;
        }
    };

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard,discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!= null){
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(!mChange){
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();;
            }
        };
        showUnsavedChangesDialog(discardButtonListener);
    }
}