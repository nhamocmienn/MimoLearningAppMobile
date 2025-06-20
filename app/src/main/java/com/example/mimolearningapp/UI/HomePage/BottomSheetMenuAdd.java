package com.example.mimolearningapp.UI.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mimolearningapp.Data.Offline.UserLocalService;
import com.example.mimolearningapp.Model.LoginResponse;
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

        View itemFlashcard = view.findViewById(R.id.item_flashcard);
        View itemFolder = view.findViewById(R.id.item_folder);
        View itemClass = view.findViewById(R.id.item_class);

        itemFlashcard.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Flashcard selected", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        itemFolder.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Folder selected", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        itemClass.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddClass.class));
            dismiss();
        });


        UserLocalService userService = new UserLocalService(requireContext());
        LoginResponse user = userService.getUser();
        if (user == null || !"teacher".equalsIgnoreCase(user.getRole())) {
            itemClass.setVisibility(View.GONE);
        }

        return view;
    }
}
