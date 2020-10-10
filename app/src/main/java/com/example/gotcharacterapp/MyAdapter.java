package com.example.gotcharacterapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private ArrayList<String> data;

    MyAdapter(ArrayList<String> data)
    {
        this.data=data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public MyViewHolder(View v){
            super(v);
            tvName = v.findViewById(R.id.tvName);
        }

    }


    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, parent,false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.tvName.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
