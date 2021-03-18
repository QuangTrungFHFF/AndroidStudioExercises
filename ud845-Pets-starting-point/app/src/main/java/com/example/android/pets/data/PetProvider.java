package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.pets.R;

public class PetProvider extends ContentProvider {

    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    private static final int PETS = 100;
    private static final int PETS_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY,PetContract.PATH_PETS,PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY,PetContract.PATH_PETS + "/#",PETS_ID);
    }

    private PetDbHelper mDbHelper;
    @Override
    public boolean onCreate() {
        // TODO: Create and initialize a PetDbHelper object to gain access to the pets database.

        mDbHelper = new PetDbHelper(getContext());
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);

        switch(match){
            case PETS:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                // TODO: Perform database query on pets table
                cursor =db.query(PetContract.PetsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case PETS_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.

                selection = PetContract.PetsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(PetContract.PetsEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new  IllegalArgumentException(getContext().getString(R.string.error_uri) + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Cursor cursor;
        long id;
        int match = sUriMatcher.match(uri);
        switch (match){
            case PETS:
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                id = db.insert(PetContract.PetsEntry.TABLE_NAME,null,values);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.error_uri) + uri);
        }

        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int rows;
        switch (match){
            case PETS:
                rows = deleteUri(uri, selection,selectionArgs);
                break;
            case PETS_ID:
                long id = ContentUris.parseId(uri);
                selection = PetContract.PetsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                rows = deleteUri(uri,selection,selectionArgs);
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.error_uri)+ uri);

        }
        return rows;
    }

    private int deleteUri(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows = db.delete(PetContract.PetsEntry.TABLE_NAME,selection,selectionArgs);
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        int rows;
        switch(match){
            case PETS:
                rows = updateUri(uri,values,selection,selectionArgs);
                break;
            case PETS_ID:
                long id = ContentUris.parseId(uri);
                selection = PetContract.PetsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(id)};
                rows = updateUri(uri,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.error_uri)+ uri);
        }
        return rows;
    }

    private int updateUri(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows = db.update(PetContract.PetsEntry.TABLE_NAME,values,selection,selectionArgs);
        return rows;
    }
}
