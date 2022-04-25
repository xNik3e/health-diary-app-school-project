package com.example.gdzieboli.feature.mainwindow.notebookAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdzieboli.R;

import java.util.List;

public class NotebookContainedRecycledViewAdapter extends RecyclerView.Adapter<NotebookContainedRecycledViewAdapter.ViewHolder> {

    private Context context;
    private List<String> additionalOrgans;

    public NotebookContainedRecycledViewAdapter(Context context, List<String> additionalOrgans) {
        this.context = context;
        this.additionalOrgans = additionalOrgans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook_sympthom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = additionalOrgans.get(position);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return additionalOrgans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.additional_organ_name);
        }
    }
}
