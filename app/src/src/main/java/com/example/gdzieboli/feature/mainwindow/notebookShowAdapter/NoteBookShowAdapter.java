package com.example.gdzieboli.feature.mainwindow.notebookShowAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Sympthom;
import com.example.gdzieboli.feature.mainwindow.notebookAdapter.NoteBookAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class NoteBookShowAdapter extends RecyclerView.Adapter<NoteBookShowAdapter.ViewHolder> {

    private Context context;
    private List<Sympthom> sympthomList;
    private int resourceID;

    public NoteBookShowAdapter(Context context, List<Sympthom> sympthomList, int resourceID) {
        this.context = context;
        this.sympthomList = sympthomList;
        this.resourceID = resourceID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_another_sympthom, parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sympthom sympthom = sympthomList.get(position);
        List<Integer> positionList = sympthom.getPositions();

        holder.sympthomName.setText(sympthom.getName());
        holder.painScale.setText(String.format("%d", sympthom.getScale()));
        if(!sympthom.getDescription().isEmpty())
            holder.description.setText(sympthom.getDescription());
        else
            holder.description.setText("");

        List<String> populatedList = populateList();
        StringBuilder sb = new StringBuilder();
        for(Integer i : positionList){
            sb.append(populatedList.get(i)).append('\n');
        }
        if(sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        else
            sb.append(" ");
        holder.painTypes.setText(sb.toString());

        randomizeColor(holder, resourceID);
    }

    @Override
    public int getItemCount() {
        return sympthomList.size();
    }

    public List<String> populateList() {
        List<String> list = new ArrayList<String>();
        list.add("Ból Łagodny");
        list.add("Ból Ostry");
        list.add("Ból Pulsujący");
        list.add("Ból Tępy");
        list.add("Ból Nagły");
        list.add("Ból Ściskający");
        list.add("Ból Kujący");
        list.add("Ból Piekący");
        return list;
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


    public void randomizeColor(ViewHolder holder, int position) {

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
                holder.descriptionLayout.setBoxStrokeColor(backgroundColor);
                holder.painTypesLayout.setBoxStrokeColor(backgroundColor);
                holder.sympthomName.setTextColor(textColor);

            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sympthomName, painScale;
        TextInputEditText description, painTypes;
        TextInputLayout descriptionLayout, painTypesLayout;

        public ViewHolder(@NonNull View view) {
            super(view);

            sympthomName = view.findViewById(R.id.edit_main_organ);
            painScale = view.findViewById(R.id.edit_pain_scale);
            description = view.findViewById(R.id.sympthom_description);
            painTypes = view.findViewById(R.id.pain_type);
            descriptionLayout = view.findViewById(R.id.sympthom_description_layout);
            painTypesLayout = view.findViewById(R.id.pain_type_layout);
        }
    }

}
