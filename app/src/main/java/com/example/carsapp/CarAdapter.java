package com.example.carsapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private ArrayList<Car> cars;
    private OnRvItemClickListener listener;

    public CarAdapter(ArrayList<Car> cars, OnRvItemClickListener listener) {
        this.cars = cars;
        this.listener = listener;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout, null, false);
        CarViewHolder viewHolder = new CarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);
        //check image
        if (car.getImage() != null && !car.getImage().isEmpty())
            holder.img.setImageURI(Uri.parse(car.getImage()));

        holder.tv_model.setText(car.getModel());
        holder.tv_color.setText(car.getColor());

        holder.tv_dpl.setText(String.valueOf(car.getDpl()));
        //holder.tv_dpl.setText(car.getDpl()+"");
        holder.img.setTag(car.getId());
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv_model, tv_color, tv_dpl;

        public CarViewHolder(@NonNull final View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.custom_card_iv);
            tv_model = itemView.findViewById(R.id.custom_car_tv_model);
            tv_color = itemView.findViewById(R.id.custom_car_tv_color);
            tv_dpl = itemView.findViewById(R.id.custom_car_tv_dpl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) img.getTag();
                    listener.onItemClick(id);
                }
            });
        }
    }
}
