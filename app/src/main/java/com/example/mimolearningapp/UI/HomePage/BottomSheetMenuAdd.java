package com.example.mimolearningapp.UI.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mimolearningapp.R;
import com.example.mimolearningapp.UI.Class.AddClass;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetMenuAdd extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_menu_add, container, false);

        view.findViewById(R.id.item_flashcard).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Flashcard selected", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        view.findViewById(R.id.item_folder).setOnClickListener(v -> {
            Toast.makeText(getContext(), "Folder selected", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        view.findViewById(R.id.item_class).setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddClass.class));
            dismiss();
        });


        return view;
    }
}
