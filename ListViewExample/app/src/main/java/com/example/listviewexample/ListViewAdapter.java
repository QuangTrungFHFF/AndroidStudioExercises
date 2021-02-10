package com.example.listviewexample;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    private Activity activity;
    private String[] items;

    public ListViewAdapter(Activity activity,String[] items)
    {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        convertView = inflater.inflate(R.layout.item_name, null);

        Button tvName = (Button) convertView.findViewById(R.id.tv_name);

        tvName.setText(items[position]);
        return convertView;
    }
}
