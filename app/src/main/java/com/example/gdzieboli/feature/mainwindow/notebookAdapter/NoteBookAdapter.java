package com.example.gdzieboli.feature.mainwindow.notebookAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.EditScreenActivity;
import com.example.gdzieboli.feature.mainwindow.ShowRecordActivity;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Sympthom;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NoteBookAdapter extends RecyclerView.Adapter<NoteBookAdapter.ViewHolder> {

    private Context context;
    private List<RecordModel> records;

    private DeleteRecord deleteRecordInterface;

    public NoteBookAdapter(Context context, List<RecordModel> records, DeleteRecord deleteRecord) {
        this.context = context;
        this.records = records;
        deleteRecordInterface = deleteRecord;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecordModel record = records.get(position);
        Date d = record.getDateAdded();
        SimpleDateFormat sdf;
        holder.main_organ_name.setText(record.getMainOrgan());
        if (!validateRecord(record)) {
            holder.error_notif.setVisibility(View.VISIBLE);
            sdf = new SimpleDateFormat("EEE MMM dd, yyyy H:mm a", new Locale("pl", "PL"));
            String result_date = sdf.format(d);
            holder.datetime.setText(result_date);
            manageAnotherOrgans(holder, record);

            holder.error_icon.startAnimation(AnimationUtils.loadAnimation(context, R.anim.warning_flashing));
        } else {
            holder.error_notif.setVisibility(View.GONE);
            sdf = new SimpleDateFormat("EEE MMM dd, yyyy H:mm a", new Locale("pl", "PL"));
            String result_date = sdf.format(d);
            holder.datetime.setText(result_date);
            manageAnotherOrgans(holder, record);
        }
        randomizeBackground(holder, record.getResourceNumber());
    }

    public void manageAnotherOrgans(ViewHolder holder, RecordModel record) {
        if(record.getAdditionalOrgans() != null && !record.getAdditionalOrgans().isEmpty()){
            holder.additionalOrgansRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            holder.additionalOrgans = record.getAdditionalOrgans();
            holder.NCRVA = new NotebookContainedRecycledViewAdapter(context, holder.additionalOrgans);
            holder.additionalOrgansRecyclerView.setLayoutManager(linearLayoutManager);
            holder.additionalOrgansRecyclerView.setAdapter(holder.NCRVA);
        }else{
            holder.additionalOrgansRecyclerView.setVisibility(View.GONE);
        }
    }

    private boolean validateRecord(RecordModel record) {
        if(record.getSympthoms()!= null && !record.getSympthoms().isEmpty()){
            Sympthom s;
            int filled = 0;
            List<String> symp_names = record.getSympthoms();
            for(String string : symp_names){
                s = (new Gson()).fromJson(string, Sympthom.class);
                if(s.getDescription()!= null && !s.getDescription().isEmpty())
                    filled += 1;
                if(s.getPositions()!= null && !s.getPositions().isEmpty())
                    filled += 1;
            }
            if(filled != 0)
                return true;
            else
                return false;
        }else
            return false;
    }

    public int getResourceId(int position) {
        switch (position) {
            case 0:
                return R.drawable.background_hue_0;
            case 1:
                return R.drawable.background_hue_1;
            case 2:
                return R.drawable.background_hue_2;
            case 3:
                return R.drawable.background_hue_3;
            case 4:
                return R.drawable.background_hue_4;
            case 5:
                return R.drawable.background_hue_5;
            case 6:
                return R.drawable.background_hue_6;
            case 7:
                return R.drawable.background_hue_7;
            case 8:
                return R.drawable.background_hue_8;
            case 9:
                return R.drawable.background_hue_9;
            case 10:
                return R.drawable.background_hue_10;
            case 11:
                return R.drawable.background_hue_11;
            case 12:
                return R.drawable.background_hue_12;
            case 13:
                return R.drawable.background_hue_13;
            case 14:
                return R.drawable.background_hue_14;
            case 15:
                return R.drawable.background_hue_15;
            case 16:
                return R.drawable.background_hue_16;
            case 17:
                return R.drawable.background_hue_17;
            case 18:
                return R.drawable.background_hue_18;
            case 19:
                return R.drawable.background_hue_19;
            case 20:
                return R.drawable.background_hue_20;
            case 21:
                return R.drawable.background_hue_21;
            case 22:
                return R.drawable.background_hue_22;
            case 23:
                return R.drawable.background_hue_23;
            case 24:
                return R.drawable.background_hue_24;
            case 25:
                return R.drawable.background_hue_25;
            case 26:
                return R.drawable.background_hue_26;
            case 27:
                return R.drawable.background_hue_27;
            case 28:
                return R.drawable.background_hue_28;
            case 29:
                return R.drawable.background_hue_29;
            case 30:
                return R.drawable.background_hue_30;
            case 31:
                return R.drawable.background_hue_31;
            case 32:
                return R.drawable.background_hue_32;
            case 33:
                return R.drawable.background_hue_33;
            case 34:
                return R.drawable.background_hue_34;
            case 35:
                return R.drawable.background_hue_35;
            case 36:
                return R.drawable.background_hue_36;
            case 37:
                return R.drawable.background_hue_37;
            case 38:
                return R.drawable.background_hue_38;
            case 39:
                return R.drawable.background_hue_39;
            case 40:
                return R.drawable.background_hue_40;
            case 41:
                return R.drawable.background_hue_41;
            case 42:
                return R.drawable.background_hue_42;
            case 43:
                return R.drawable.background_hue_43;
            case 44:
                return R.drawable.background_hue_44;
            case 45:
                return R.drawable.background_hue_45;
            case 46:
                return R.drawable.background_hue_46;
            default:
                return R.drawable.background_hue_47;
        }
    }


    public void randomizeBackground(ViewHolder holder, int position) {

        Bitmap background = BitmapFactory.decodeResource(context.getResources(), getResourceId(position));

        //w tym momencie newBackgroud ma zmieniony kolor i mogę zabawić się paletą
        Palette.from(background).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette p) {

                int backgroundColor = ContextCompat.getColor(context, R.color.buttonBackground);
                int textColor = ContextCompat.getColor(context, R.color.white);

                if (p.getVibrantSwatch() != null) {
                    backgroundColor = p.getVibrantSwatch().getRgb();
                    textColor = p.getVibrantSwatch().getBodyTextColor();
                } else if (p.getDarkVibrantSwatch() != null) {
                    backgroundColor = p.getDarkVibrantSwatch().getRgb();
                    textColor = p.getDarkVibrantSwatch().getTitleTextColor();
                } else if (p.getLightVibrantSwatch() != null) {
                    backgroundColor = p.getLightVibrantSwatch().getRgb();
                    textColor = p.getLightVibrantSwatch().getBodyTextColor();
                } else if (p.getLightMutedSwatch() != null) {
                    backgroundColor = p.getLightMutedSwatch().getRgb();
                    textColor = p.getLightMutedSwatch().getBodyTextColor();
                } else if (p.getMutedSwatch() != null) {
                    backgroundColor = p.getMutedSwatch().getRgb();
                    textColor = p.getMutedSwatch().getTitleTextColor();
                } else if (p.getDarkMutedSwatch() != null) {
                    backgroundColor = p.getDarkMutedSwatch().getRgb();
                    textColor = p.getDarkMutedSwatch().getTitleTextColor();
                } else {
                    backgroundColor = p.getDominantSwatch().getRgb();
                    textColor = p.getDominantSwatch().getTitleTextColor();
                }

                holder.viewRaport.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                holder.viewRaport.setTextColor(textColor);
                holder.editRaport.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                holder.editRaport.setTextColor(textColor);
                holder.deleteRaport.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                holder.deleteRaport.setTextColor(textColor);
                holder.background.setImageBitmap(background);
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView datetime, main_organ_name;
        LinearLayout error_notif;
        Button viewRaport, editRaport, deleteRaport;
        RecyclerView additionalOrgansRecyclerView;
        NotebookContainedRecycledViewAdapter NCRVA;
        List<String> additionalOrgans;
        ImageView background, error_icon;

        public ViewHolder(@NonNull View v) {
            super(v);

            datetime = v.findViewById(R.id.date_and_time);
            error_notif = v.findViewById(R.id.error_notif);
            main_organ_name = v.findViewById(R.id.main_organ_name);
            viewRaport = v.findViewById(R.id.button_show);
            editRaport = v.findViewById(R.id.button_edit);
            deleteRaport = v.findViewById(R.id.button_remove);
            additionalOrgansRecyclerView = v.findViewById(R.id.recycler_view_sympthoms);
            background = v.findViewById(R.id.image_background);
            error_icon = v.findViewById(R.id.error_notif_icon);

            deleteRaport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                    builder.setMessage("Czy chcesz usunąć ten wpis w dzienniku?")
                            .setTitle("Uwaga")
                            .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteRecordInterface.deleteR(getAdapterPosition(), records.get(getAdapterPosition()).getId());
                                }
                            })
                            .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });

            editRaport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditScreenActivity.class);
                    intent.putExtra("isEditData", true);
                    intent.putExtra("recordID", records.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
            viewRaport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowRecordActivity.class);
                    intent.putExtra("recordID", records.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }

    public interface DeleteRecord {
        void deleteR(int position, int recordId);
    }
}
