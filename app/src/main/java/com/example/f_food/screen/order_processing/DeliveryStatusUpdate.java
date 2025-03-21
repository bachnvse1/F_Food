package com.example.f_food.screen.order_processing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f_food.R;
import com.example.f_food.adapter.FoodAcceptShippingAdapter;
import com.example.f_food.adapter.FoodUpdateScreenAdapter;
import com.example.f_food.entity.Food;
import com.example.f_food.repository.FoodRepository;
import com.example.f_food.repository.OrderDetailRepository;
import com.example.f_food.repository.OrderRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class DeliveryStatusUpdate extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView tvOrderId, tvRestaurantAddress, tvDeliveryAddress, tvDeliveryTime, tvCost;
    private RecyclerView foodRecyclerView;
    private FoodUpdateScreenAdapter foodAdapter;
    private OrderRepository orderRepository;
    private RadioGroup rgStatus;
    private Button btnUpdate;
    private int orderId;
    private FoodRepository foodRepository;
    private OrderDetailRepository orderDetailRepository;

    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status_update);

        // 🟢 Khởi tạo Repository
        orderRepository = new OrderRepository(this);
        orderDetailRepository = new OrderDetailRepository(this);
        foodRepository = new FoodRepository(this);

        // 🟢 Ánh xạ các thành phần giao diện
        tvOrderId = findViewById(R.id.orderId);
        tvRestaurantAddress = findViewById(R.id.restaurantAddress);
        tvDeliveryAddress = findViewById(R.id.deliveryAddress);
        tvDeliveryTime = findViewById(R.id.deliveryTime);
        tvCost = findViewById(R.id.foodCost);
        foodRecyclerView = findViewById(R.id.foodListAcceptShipping);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 🟢 Cấu hình RecyclerView
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🟢 Lấy dữ liệu từ Intent
        int orderId = getIntent().getIntExtra("orderId", -1);
        String restaurantAddress = getIntent().getStringExtra("restaurantAddress");
        String deliveryAddress = getIntent().getStringExtra("deliveryAddress");
        String deliveryTime = getIntent().getStringExtra("deliveryTime");
        double cost = getIntent().getDoubleExtra("cost", 0.0);

        // 🟢 Hiển thị dữ liệu đơn hàng
        tvOrderId.setText("Order ID: " + orderId);
        tvRestaurantAddress.setText("Restaurant: " + restaurantAddress);
        tvDeliveryAddress.setText("Delivery Address: " + deliveryAddress);
        tvDeliveryTime.setText("Delivery Time: " + deliveryTime);
        tvCost.setText("Total Cost: $" + cost);

        // 🟢 Lấy danh sách món ăn từ Order ID
        foodList = orderRepository.getFoodListByOrderId(orderId);

        if (foodList != null && !foodList.isEmpty()) {
            foodAdapter = new FoodUpdateScreenAdapter(foodList);
            foodRecyclerView.setAdapter(foodAdapter);
        } else {
            Log.d("AcceptShippingOrder", "Không có món ăn nào trong đơn hàng.");
        }

        // 🟢 Xử lý `BottomNavigationView`
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_delivery);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) { // 🔹 Quay lại PendingOrder
                    startActivity(new Intent(DeliveryStatusUpdate.this, PendingOrder.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.nav_orders) { // 🔹 Chuyển sang DeliveryHistory
                    startActivity(new Intent(DeliveryStatusUpdate.this, DeliveryHistory.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                } else if (itemId == R.id.nav_delivery) { // 🔹 Giữ nguyên trang này
                    return true;
                }

                return false;
            }
        });

        // 🟢 Xử lý khi nhấn nút cập nhật trạng thái đơn hàng
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedStatusId = rgStatus.getCheckedRadioButtonId();
                String newStatus = "";

                if (selectedStatusId == R.id.rb_processing) {
                    newStatus = "Processing";
                } else if (selectedStatusId == R.id.rb_out_for_delivery) {
                    newStatus = "Out for Delivery";
                } else if (selectedStatusId == R.id.rb_delivered) {
                    newStatus = "Delivered";
                }

                if (!newStatus.isEmpty()) {
                    orderRepository.updateOrderStatus(orderId, newStatus);
                    Toast.makeText(DeliveryStatusUpdate.this, "Order #" + orderId + " updated to '" + newStatus + "'", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeliveryStatusUpdate.this, "Please select a status!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
