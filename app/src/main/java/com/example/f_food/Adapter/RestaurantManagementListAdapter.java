package com.example.f_food.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f_food.Entity.Restaurant;
import com.example.f_food.R;

import java.util.List;

public class RestaurantManagementListAdapter extends RecyclerView.Adapter<RestaurantManagementListAdapter.ViewHolder> {
    private List<Restaurant> restaurantList;

    public RestaurantManagementListAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
     private TextView nameText, addressText, phoneText, statusText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.txtrestaurant_Management_Name);
            addressText = itemView.findViewById(R.id.txtrestaurant_Management_Address);
            phoneText = itemView.findViewById(R.id.txtrestaurant_Management_Phone);
            statusText = itemView.findViewById(R.id.txtrestaurant_Management_Status);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant_manager, parent, false);
        return new ViewHolder(view);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.nameText.setText("Name: " + restaurant.getName());
        holder.addressText.setText("Address: " + restaurant.getAddress());
        holder.phoneText.setText("Phone: " + restaurant.getPhone());
        holder.statusText.setText("Status: " + restaurant.getStatus());
    }


    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

}
