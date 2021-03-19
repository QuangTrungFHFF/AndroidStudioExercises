package com.example.android.pets.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.pets.R;

import java.util.zip.Inflater;

public class PetCursorAdapter extends CursorAdapter {
    public PetCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = null;
        View view = layoutInflater.from(context).inflate(R.layout.single_pet_item,parent,false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvPetName, tvPetBreed;
        tvPetName = (TextView)view.findViewById(R.id.pet_name);
        tvPetBreed = (TextView)view.findViewById(R.id.pet_breed);

        String mPetName = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetsEntry.COLUMN_NAME));
        String mPetBreed = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetsEntry.COLUMN_BREED));

        tvPetName.setText(mPetName);
        tvPetBreed.setText(mPetBreed);
    }
}
