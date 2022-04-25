package com.example.gdzieboli.feature.mainwindow.addMoreWindow;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Organ;

import java.util.List;

public class OrganAdapter extends RecyclerView.Adapter<OrganAdapter.ViewHolder> {

    Context context;
    List<Organ> organs;

    public OrganAdapter(Context context, List<Organ> organs) {
        this.context = context;
        this.organs = organs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_more_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Organ organ = organs.get(position);
        holder.description.setText(organ.getName());
        if(organ.isSelected()) {
            int colorFrom = context.getResources().getColor(R.color.white);
            int colorTo = context.getResources().getColor(R.color.colorPrimary);
            changeColor(colorFrom, colorTo, holder.view);
        }else{
            holder.view.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                organ.setSelected(!organ.isSelected());
                if(organ.isSelected()) {
                    int colorFrom = context.getResources().getColor(R.color.white);
                    int colorTo = context.getResources().getColor(R.color.colorPrimary);
                    changeColor(colorFrom, colorTo, holder.view);
                }else{
                    holder.view.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                holder.changeIcon(organ.isSelected());
            }
        });
        holder.changeIcon(organ.isSelected());
    }

    @Override
    public int getItemCount() {
        return organs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView check_status;
        TextView description;
        View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            check_status = itemView.findViewById(R.id.img_check_if_selected);
            description = itemView.findViewById(R.id.organ_name);
            view = itemView;
        }
        public void changeIcon(Boolean isSelected){
            if(isSelected){
                check_status.setImageResource(R.drawable.ic_check);
                check_status.setTag(R.drawable.ic_check);
            }
            else{
                check_status.setImageResource(0);
                check_status.setTag(0);

            }
        }
    }
    public void changeColor(int from, int to, View view){
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), from, to);
        colorAnimator.setDuration(250);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimator.start();
    }



}
