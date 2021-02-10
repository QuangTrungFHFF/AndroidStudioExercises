package com.example.recycleviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {

    private List<Person> people;
    private Context context;

    public RecyclerDataAdapter(List<Person> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        switch(viewType)
        {
            case 1:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_names,parent,false);
                break;
            case 2:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_names_female,parent,false);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_names,parent,false);
                break;
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String name = people.get(position).getName();
            holder.tvName.setText(name);
    }

    @Override
    public int getItemCount() {
        return people == null? 0 : people.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Cài đặt kiểu item view type cho từng phần tử, nếu có giới tính là nam thì trả về 1, nữ thì trả về 2.
        if (people.get(position).isMale()) {
            return 1;
        } else {
            return 2;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
