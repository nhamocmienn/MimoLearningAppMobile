package com.example.mimolearningapp.UI.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mimolearningapp.ActionSystem.LoadingDialog;
import com.example.mimolearningapp.Data.Offline.UserLocalService;
import com.example.mimolearningapp.Data.Online.ApiClient;
import com.example.mimolearningapp.Data.Online.UserApiService;
import com.example.mimolearningapp.Model.LoginRequest;
import com.example.mimolearningapp.Model.LoginResponse;
import com.example.mimolearningapp.Model.UpdateProfileRequest;
import com.example.mimolearningapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageProfile extends AppCompatActivity {

    ImageView imgAvatar;
    TextView tvName, tvRole;
    TextView tvEmail;
    Button btnUpdate;
    Button btnLogout, btnDeleteAccount;

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
        tvEmail = findViewById(R.id.tvEmail);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnLogout = findViewById(R.id.btnLogout);
        //btnDeleteAccount = findViewById(R.id.btnDeleteAccount);


        // Khởi tạo service
        userLocalService = new UserLocalService(this);
        apiService = ApiClient.getRetrofitInstance().create(UserApiService.class);

        // Load user từ SQLite
        currentUser = userLocalService.getUser();

        if (currentUser != null) {
            tvName.setText(currentUser.getName());
            tvRole.setText(currentUser.getRole());
            tvEmail.setText(currentUser.getEmail());
        }
        else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Cập nhật khi nhấn nút
        btnUpdate.setOnClickListener(v -> updateProfile());

        //Dang xuat
        btnLogout.setOnClickListener(v -> {
            userLocalService.clearUser();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Login.class));
            finish();
        });

//        btnDeleteAccount.setOnClickListener(v -> {
//            // Tạo dialog nhập mật khẩu xác nhận
//            final EditText edtPassword = new EditText(this);
//            edtPassword.setHint("Nhập mật khẩu để xác nhận");
//            edtPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            int padding = (int) (16 * getResources().getDisplayMetrics().density);
//            edtPassword.setPadding(padding, padding, padding, padding);
//
//            new android.app.AlertDialog.Builder(this)
//                    .setTitle("Xóa tài khoản")
//                    .setMessage("Bạn có chắc muốn xóa tài khoản này? Hành động này không thể hoàn tác.")
//                    .setView(edtPassword)
//                    .setPositiveButton("Xác nhận", (dialog, which) -> {
//                        String password = edtPassword.getText().toString().trim();
//                        if (TextUtils.isEmpty(password)) {
//                            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        LoadingDialog loadingDialog = new LoadingDialog(this);
//                        loadingDialog.show();
//
//                        LoginRequest request = new LoginRequest(currentUser.getEmail(), password);
//                        apiService.deleteAccount(request).enqueue(new Callback<Void>() {
//                            @Override
//                            public void onResponse(Call<Void> call, Response<Void> response) {
//                                loadingDialog.dismiss();
//                                if (response.isSuccessful()) {
//                                    userLocalService.clearUser();
//                                    Toast.makeText(ManageProfile.this, "Đã xóa tài khoản", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(ManageProfile.this, Login.class));
//                                    finish();
//                                } else {
//                                    Toast.makeText(ManageProfile.this, "Mật khẩu không đúng hoặc lỗi khi xóa", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<Void> call, Throwable t) {
//                                loadingDialog.dismiss();
//                                Toast.makeText(ManageProfile.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    })
//                    .setNegativeButton("Hủy", null)
//                    .show();
//        });
    }

    private void updateProfile() {
        // Tạo layout custom cho dialog
        final EditText edtName = new EditText(this);
        edtName.setHint("Tên");

        final EditText edtEmail = new EditText(this);
        edtEmail.setHint("Email");

        final EditText edtPassword = new EditText(this);
        edtPassword.setHint("Mật khẩu");
        edtPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        final EditText edtConfirmPassword = new EditText(this);
        edtConfirmPassword.setHint("Xác nhận mật khẩu");
        edtConfirmPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);
        layout.addView(edtName);
        layout.addView(edtEmail);
        layout.addView(edtPassword);
        layout.addView(edtConfirmPassword);

        edtName.setText(currentUser.getName());
        edtEmail.setText(currentUser.getEmail());

        new android.app.AlertDialog.Builder(this)
                .setTitle("Cập nhật thông tin")
                .setView(layout)
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    String name = edtName.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();
                    String confirmPassword = edtConfirmPassword.getText().toString().trim();

                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
                        Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    LoadingDialog loadingDialog = new LoadingDialog(this);
                    loadingDialog.show();

                    UpdateProfileRequest request = new UpdateProfileRequest(currentUser.getUserId(), email, password);
                    apiService.updateProfile(request).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            loadingDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                userLocalService.saveUser(response.body());
                                tvName.setText(response.body().getName());
                                tvRole.setText(response.body().getRole());
                                tvEmail.setText(response.body().getEmail());
                                Toast.makeText(ManageProfile.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ManageProfile.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            loadingDialog.dismiss();
                            Toast.makeText(ManageProfile.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
