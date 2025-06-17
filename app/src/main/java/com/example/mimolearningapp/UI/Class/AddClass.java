package com.example.mimolearningapp.UI.Class;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mimolearningapp.R;

public class AddClass extends AppCompatActivity {
    EditText editTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_class);

        editTitle = findViewById(R.id.editTitle);
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();

            // Xử lý lưu dữ liệu tại đây
            Toast.makeText(this, "Saved: " + title, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}