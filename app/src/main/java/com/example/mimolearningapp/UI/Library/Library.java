package com.example.mimolearningapp.UI.Library;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.mimolearningapp.R;
import com.example.mimolearningapp.UI.Class.ClassFragment;
import com.example.mimolearningapp.UI.HomePage.BottomSheetMenuAdd;
import com.example.mimolearningapp.UI.HomePage.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Library extends AppCompatActivity {

    Button btnFlashcardSets,btnClasses,btnFolders;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library);

        // Gán view từ layout
        btnFlashcardSets = findViewById(R.id.btnFlashcardSets);
        btnClasses = findViewById(R.id.btnClasses);
        btnFolders = findViewById(R.id.btnFolders);
        bottomNav = findViewById(R.id.bottomNav);

        // Mặc định load fragment đầu tiên (ClassFragment)
        replaceFragment(new ClassFragment());
        highlightButton(btnClasses);

        // Thiết lập sự kiện click cho các nút
        btnFlashcardSets.setOnClickListener(v -> {
            // TODO: Replace bằng FlashcardFragment nếu có
            replaceFragment(new ClassFragment()); // giả sử chưa có FlashcardFragment
            highlightButton(btnFlashcardSets);
        });

        btnClasses.setOnClickListener(v -> {
            replaceFragment(new ClassFragment());
            highlightButton(btnClasses);
        });

        btnFolders.setOnClickListener(v -> {
            // TODO: Replace bằng FolderFragment nếu có
            replaceFragment(new ClassFragment()); // giả sử chưa có FolderFragment
            highlightButton(btnFolders);
        });
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_create) {
                new BottomSheetMenuAdd().show(getSupportFragmentManager(), "BottomSheetMenu");
                return true;
            } else if (item.getItemId() == R.id.nav_library) {
                // Đang ở Library rồi
                return true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }

    private void highlightButton(Button selectedBtn) {
        btnFlashcardSets.setBackgroundResource(R.drawable.tab_button_selector);
        btnClasses.setBackgroundResource(R.drawable.tab_button_selector);
        btnFolders.setBackgroundResource(R.drawable.tab_button_selector);
        selectedBtn.setBackgroundResource(R.drawable.tab_button_selected);
    }
}