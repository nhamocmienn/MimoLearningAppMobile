package com.example.mimolearningapp.UI.Class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mimolearningapp.ActionSystem.LoadingDialog;
import com.example.mimolearningapp.Adapter.ClassAdapter;
import com.example.mimolearningapp.Data.Online.ApiClient;
import com.example.mimolearningapp.Data.Online.ClassApiService;
import com.example.mimolearningapp.Model.ClassItem;
import com.example.mimolearningapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassFragment extends Fragment {

    private RecyclerView recyclerView;
    private ClassAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerClassList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadClasses();
    }

    private void loadClasses() {
        LoadingDialog loadingDialog = new LoadingDialog(requireContext());
        loadingDialog.show();

        ClassApiService apiService = ApiClient.getClassApiService();
        apiService.getAllClasses().enqueue(new Callback<List<ClassItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<ClassItem>> call,
                                   @NonNull Response<List<ClassItem>> response) {
                loadingDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new ClassAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ClassItem>> call, @NonNull Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(getContext(), "Lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}