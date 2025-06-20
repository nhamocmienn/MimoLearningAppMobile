package com.example.mimolearningapp.UI.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mimolearningapp.ActionSystem.LoadingDialog;
import com.example.mimolearningapp.Data.Offline.UserLocalService;
import com.example.mimolearningapp.Data.Online.ApiClient;
import com.example.mimolearningapp.Data.Online.UserApiService;
import com.example.mimolearningapp.Model.LoginRequest;
import com.example.mimolearningapp.Model.LoginResponse;
import com.example.mimolearningapp.R;
import com.example.mimolearningapp.UI.HomePage.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    TextView tvRegister, tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(v -> {
            String email = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            login(email, password);
        });

        tvRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Chuyển sang trang đăng ký...", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, RegisterActivity.class));
        });

        tvForgotPassword.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng quên mật khẩu đang phát triển", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
    }

    private void login(String email, String password) {
        UserApiService api = ApiClient.getUserApiService();
        LoginRequest request = new LoginRequest(email, password);

        LoadingDialog loadingDialog = new LoadingDialog(Login.this);
        loadingDialog.show();


        api.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loadingDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu user vào SQLite bằng module riêng
                    UserLocalService localService = new UserLocalService(Login.this);
                    localService.saveUser(response.body());

                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(Login.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
