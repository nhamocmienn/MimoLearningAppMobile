package com.example.mimolearningapp.UI.User;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mimolearningapp.Data.Offline.UserLocalService;
import com.example.mimolearningapp.Data.Online.ApiClient;
import com.example.mimolearningapp.Data.Online.UserApiService;
import com.example.mimolearningapp.Model.LoginResponse;
import com.example.mimolearningapp.Model.UpdateProfileRequest;
import com.example.mimolearningapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProfile extends AppCompatActivity {

    ImageView imgAvatar;
    TextView tvName, tvRole;
    EditText edtEmail, edtPassword;
    Button btnUpdate;

    UserLocalService userLocalService;
    UserApiService apiService;
    LoginResponse currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        // Ánh xạ view
        imgAvatar = findViewById(R.id.imgAvatar);
        tvName = findViewById(R.id.tvName);
        tvRole = findViewById(R.id.tvRole);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnUpdate = findViewById(R.id.btnUpdate);

        // Khởi tạo service
        userLocalService = new UserLocalService(this);
        apiService = ApiClient.getRetrofitInstance().create(UserApiService.class);

        // Load user từ SQLite
        currentUser = userLocalService.getUser();

        if (currentUser != null) {
            tvName.setText(currentUser.getName());
            tvRole.setText(currentUser.getRole());
            edtEmail.setText(currentUser.getEmail());
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Cập nhật khi nhấn nút
        btnUpdate.setOnClickListener(v -> updateProfile());
    }

    private void updateProfile() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateProfileRequest request = new UpdateProfileRequest(currentUser.getUserId(), email, password);
        apiService.updateProfile(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu lại vào SQLite
                    userLocalService.saveUser(response.body());

                    // Hiển thị lại nếu cần
                    tvName.setText(response.body().getName());
                    tvRole.setText(response.body().getRole());

                    Toast.makeText(ManageProfile.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageProfile.this, "Lỗi cập nhật. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ManageProfile.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
