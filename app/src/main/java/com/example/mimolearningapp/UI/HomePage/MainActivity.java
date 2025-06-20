package com.example.mimolearningapp.UI.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mimolearningapp.Data.Offline.SQLiteHelper;
import com.example.mimolearningapp.Data.Offline.UserLocalService;
import com.example.mimolearningapp.Model.LoginResponse;
import com.example.mimolearningapp.R;
import com.example.mimolearningapp.UI.Library.Library;
import com.example.mimolearningapp.UI.User.Login;
import com.example.mimolearningapp.UI.User.ManageProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    ImageView imgProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        View rootView = findViewById(R.id.main);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // <-- fix tại đây
            return insets;
        });

        checkLogin();

        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_create) {
                new BottomSheetMenuAdd().show(getSupportFragmentManager(), "BottomSheetMenu");
                return true;
            } else if (item.getItemId() == R.id.nav_home) {
                // MainActivity rồi, không làm gì cả
                return true;
            } else if (item.getItemId() == R.id.nav_library) {
                startActivity(new Intent(this, Library.class));
                return true;
            }
            return false;
        });

        ImageView imgProfile = findViewById(R.id.imgProfile); // ảnh đại diện ở MainActivity
        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManageProfile.class);
            startActivity(intent);
        });


    }

    public void checkLogin(){
        UserLocalService userLocalService = new UserLocalService(this);
        LoginResponse user = userLocalService.getUser();
        if (user == null || user.getUserId()==0) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}