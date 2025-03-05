package com.example.f_food.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.f_food.R;
import com.example.f_food.Entity.Food;
import com.squareup.picasso.Picasso;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.List;
import java.util.Objects;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    private List<Food> foodList;
    private OnItemClickListener listener;

    private static Context context;

    public interface OnItemClickListener {
        void onItemClick(int foodId);
    }

    public FoodListAdapter(Context context, List<Food> foodList, OnItemClickListener listener) {
        this.context = context;
        this.foodList = foodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.bind(food, listener);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productDescription, productPrice, productStockStatus;
        private ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productPrice = itemView.findViewById(R.id.productPrice);
            productStockStatus = itemView.findViewById(R.id.productStockStatus);
            productImage = itemView.findViewById(R.id.productImage);
        }

        public void bind(final Food food, final OnItemClickListener listener) {
            productName.setText(food.getName());
            productDescription.setText(food.getDescription());
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            productPrice.setText(formatter.format(food.getPrice()) + " VNĐ");
            productStockStatus.setText(food.getStockStatus());
            if(food.getImageUrl() != null && !food.getImageUrl().isEmpty()) {
                Picasso.get()
                        .load(food.getImageUrl())
                        .resize(500, 500)
                        .centerCrop()
                        .into(productImage);
            }
            if(Objects.equals(food.getStockStatus(), "Available")) {
                productStockStatus.setTextColor(Color.parseColor("#006400"));
            } else productStockStatus.setTextColor(Color.RED);
            itemView.setOnClickListener(v -> listener.onItemClick(food.getFoodId()));
        }
    }
}
