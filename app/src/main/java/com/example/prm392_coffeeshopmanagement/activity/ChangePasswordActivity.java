package com.example.prm392_coffeeshopmanagement.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.entity.User;
import com.example.prm392_coffeeshopmanagement.utils.PasswordUtils;
import com.example.prm392_coffeeshopmanagement.viewmodel.UserViewModel;

public class ChangePasswordActivity extends BaseActivity {

    private TextView tvUserName;
    private EditText edOldPassword;
    private EditText edNewPassword;
    private EditText edConfirmPassword;
    private Button btnChangePassword;
    private UserViewModel userViewModel;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        tvUserName = findViewById(R.id.tvUserNameChangePassword);
        edOldPassword = findViewById(R.id.edOldPassword);
        edNewPassword = findViewById(R.id.edNewPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            User user = userViewModel.getUserByUserName(username);
            if (user != null) {
                tvUserName.setText(username);
                btnChangePassword.setOnClickListener(v -> onChangePasswordButtonClick(username
                ));
            }

        }


    }
    private void onChangePasswordButtonClick(String username) {
        String oldPassword = edOldPassword.getText().toString().trim();
        String newPassword = edNewPassword.getText().toString().trim();
        String confirmPassword = edConfirmPassword.getText().toString().trim();
        if(oldPassword.isEmpty()) {
            showErrorMessage("Old password is required!");
            return;
        }

        if(newPassword.isEmpty()) {
            showErrorMessage("New password is required!");
            return;
        }

        if(confirmPassword.isEmpty()) {
            showErrorMessage("Confirm password is required!");
            return;
        }

        // Kiểm tra xem oldPassword có đúng hay không
        // Giả sử userId có trong SharedPreferences hoặc ta lấy user theo oldPassword
        // Dưới đây ví dụ so sánh DB bằng getUserByPassword(...):
        User user = userViewModel.getUserByUserName(username);
        if(!oldPassword.equals(user.getPassword())) {
            showErrorMessage("Old password is not correct!");
            return;
        }
      if (newPassword.equals(oldPassword)){
          showErrorMessage("New password cannot be the same as the old password!");
          return;
      }

        // Kiểm tra newPassword == confirmPassword
        if (!newPassword.equals(confirmPassword)) {
            showErrorMessage("New password and Confirm password do not match!");
            return;
        }

        // Kiểm tra newPassword có hợp lệ không
        if (!PasswordUtils.validatePassword(newPassword)) {
            showErrorMessage("New password must begin with an uppercase letter, "
                    + "be at least 6 characters, and contain at least one special character.");
            return;
        }

        // Mọi thứ OK, tiến hành cập nhật
        userViewModel.changePassword(user.getUserName(), newPassword);
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình đăng nhập
        navigateToLoginScreen();
    }

    private void showErrorMessage(String msg) {
        // Bạn có thể show Toast, hoặc setError(...) tuỳ ý
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void navigateToLoginScreen() {
        // Ví dụ chuyển sang LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}