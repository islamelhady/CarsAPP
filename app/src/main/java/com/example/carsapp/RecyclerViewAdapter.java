package com.example.carsapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    ArrayList<Car> cars;

    public RecyclerViewAdapter(ArrayList<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout, null, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Car c = cars.get(position);
        holder.text_name.setText(c.getModel());
        holder.text_color.setText(c.getColor());
        holder.text_dpl.setText(c.getDpl()+"");


    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView text_name, text_color, text_dpl;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            text_name = itemView.findViewById(R.id.textName);
            text_color = itemView.findViewById(R.id.textColor);
            text_dpl = itemView.findViewById(R.id.textDpl);
        }
    }
}
