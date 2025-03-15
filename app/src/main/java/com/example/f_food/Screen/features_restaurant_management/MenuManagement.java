package com.example.f_food.Screen.features_restaurant_management;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.f_food.Adapter.FoodListAdapter;
import com.example.f_food.Entity.Food;
import com.example.f_food.R;
import com.example.f_food.Repository.FoodRepository;
import java.util.List;

public class MenuManagement extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodListAdapter adapter;
    private FoodRepository repository;
    private final int restaurantId = 1; // ID nhà hàng bạn muốn lấy dữ liệu.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_management);

        recyclerView = findViewById(R.id.recyclerViewFoods);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new FoodRepository(getApplicationContext());

        // Lấy dữ liệu trực tiếp từ repository
        List<Food> foods = repository.getFoodsByRestaurantId(restaurantId);

        adapter = new FoodListAdapter(this, foods, foodId -> {
            Intent intent = new Intent(MenuManagement.this, ManageFoodDetail.class);
            intent.putExtra("food_id", foodId);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}