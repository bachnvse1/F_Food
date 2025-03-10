package com.example.f_food.Screen.authentication_authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.f_food.Entity.User;
import com.example.f_food.MainActivity;
import com.example.f_food.R;
import com.example.f_food.Repository.UserRepository;
import com.example.f_food.Screen.features_customer.OrderHistory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnLoginForPartner;
    private TextView tvForgotPassword;
    private UserRepository userRepository;
    ImageView imgLogoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ UI
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLoginForPartner = findViewById(R.id.btnLoginForPartner);  // Initialize the Login for Partner button
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        imgLogoLogin = findViewById(R.id.imgLogoLogin);

        Picasso.get()
                .load(R.drawable.login)
                .resize(500, 500)
                .centerCrop()
                .into(imgLogoLogin);

        // Khởi tạo repository
        userRepository = new UserRepository(this);

        // Xử lý sự kiện khi nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> handleLogin());

        // Xử lý sự kiện khi nhấn nút đăng nhập cho partner
        btnLoginForPartner.setOnClickListener(v -> navigateToRestaurantLogIn());
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy danh sách user từ database
        List<User> users = userRepository.getAllUsers();

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // Kiểm tra UserType là "Customer"
                if ("Customer".equals(user.getUserType())) {
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                    // Chuyển sang màn hình khác sau khi đăng nhập
                    Intent intent = new Intent(this, OrderHistory.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    Toast.makeText(this, "Bạn không phải là khách hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        // Nếu không tìm thấy user phù hợp
        Toast.makeText(this, "Email hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
    }


    // Method to navigate to restaurant login screen
    private void navigateToRestaurantLogIn() {
        Intent intent = new Intent(this, RestaurantLogIn.class);  // Assuming RestaurantLogInActivity is your target activity
        startActivity(intent);
    }
}
