package com.example.mimolearningapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mimolearningapp.Model.ClassItem;
import com.example.mimolearningapp.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassItem> classList;

    public ClassAdapter(List<ClassItem> classList) {
        this.classList = classList;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassItem item = classList.get(position);
        holder.tvClassName.setText(item.getName());
        holder.tvCreatorId.setText("Creator ID: " + item.getCreatorId());
    }

    @Override
    public int getItemCount() {
        return classList != null ? classList.size() : 0;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvCreatorId;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvCreatorId = itemView.findViewById(R.id.tvCreatorId);
        }
    }
}
