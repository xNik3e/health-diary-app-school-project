package com.example.gdzieboli.feature.mainwindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Sympthom;
import com.example.gdzieboli.feature.mainwindow.edit_adapter.EditScreenAdapter;
import com.example.gdzieboli.feature.mainwindow.notebookShowAdapter.NoteBookShowAdapter;
import com.example.gdzieboli.utils.database.DbHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowRecordActivity extends AppCompatActivity {

    private TextView mainOrgan, dateTime;
    private RecyclerView additionalOrgans;
    private TextInputEditText description;
    private TextInputLayout descriptionLayout;
    private RecordModel recordModel;
    private DbHelper dbHelper;
    private List<Sympthom> sympthomList = new ArrayList<>();
    private LinearLayout linearLayout;
    private NoteBookShowAdapter noteBookShowAdapter;
    private ImageView background;


    //IF NOTHING IS SELECTED
    private TextInputLayout lonelySympthomDescriptionLayout;
    private TextInputLayout lonelySympthomPainTypesLayout;
    private TextInputEditText lonelySympthomDescription;
    private TextInputEditText lonelySympthomPainTypes;
    private TextView lonelySympthomPainScale;
    private Sympthom lonelySympthom;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_record);

        mainOrgan = findViewById(R.id.main_organ_name);
        dateTime = findViewById(R.id.date_and_time);
        additionalOrgans = findViewById(R.id.recycler_view_view_sympthoms);
        description = findViewById(R.id.edt_text_description);
        descriptionLayout = findViewById(R.id.edt_text_description_layout);
        background = findViewById(R.id.image_background);
        //IF NOTHING IS SELECTED
        lonelySympthomDescriptionLayout = findViewById(R.id.sympthom_description_layout);
        lonelySympthomPainTypesLayout = findViewById(R.id.pain_type_layout);
        linearLayout = findViewById(R.id.linear_layout_empty_symptom);
        lonelySympthomDescription = findViewById(R.id.sympthom_description);
        lonelySympthomPainTypes = findViewById(R.id.pain_type);
        lonelySympthomPainScale = findViewById(R.id.edit_pain_scale);

        dbHelper = new DbHelper(this);

        int recordId = getIntent().getIntExtra("recordID", 0);
        recordModel = dbHelper.getRecord(recordId + "");

        Bitmap bg = BitmapFactory.decodeResource(getResources(), getResourceId(recordModel.getResourceNumber()));
        background.setImageBitmap(bg);

        mainOrgan.setText(recordModel.getMainOrgan());
        description.setText(recordModel.getDesciption());
        Date d = recordModel.getDateAdded();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("EEE MMM dd, yyyy H:mm a", new Locale("pl", "PL"));
        String result_date = sdf.format(d);
        dateTime.setText(result_date);

        if (recordModel.getAdditionalOrgans() != null && !recordModel.getAdditionalOrgans().isEmpty()) {
            if (recordModel.getSympthoms() != null && !recordModel.getSympthoms().isEmpty()) {
                List<String> listOfSympthoms = recordModel.getSympthoms();
                for (String s : listOfSympthoms) {
                    Sympthom sympthom = (new Gson()).fromJson(s, Sympthom.class);
                    sympthomList.add(sympthom);
                }
            } else {
                for (String s : recordModel.getAdditionalOrgans()) {
                    sympthomList.add(new Sympthom(s));
                }
            }
            linearLayout.setVisibility(View.GONE);
            additionalOrgans.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            additionalOrgans.setLayoutManager(linearLayoutManager);
            noteBookShowAdapter = new NoteBookShowAdapter(this, sympthomList, recordModel.getResourceNumber());
            additionalOrgans.setAdapter(noteBookShowAdapter);
            randomizeColor(recordModel.getResourceNumber(), true);
        } else {
            additionalOrgans.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            if (recordModel.getSympthoms() != null && !recordModel.getSympthoms().isEmpty()) {
                lonelySympthom = (new Gson()).fromJson(recordModel.getSympthoms().get(0), Sympthom.class);
                lonelySympthomDescription.setText(lonelySympthom.getDescription());
                List<Integer> positionList = lonelySympthom.getPositions();
                List<String> populatedList = populateList();
                StringBuilder sb = new StringBuilder();
                for(Integer i : positionList){
                    sb.append(populatedList.get(i)).append('\n');
                }
                if(sb.length() > 1)
                    sb.deleteCharAt(sb.length() - 1);
                else
                    sb.append(" ");
                lonelySympthomPainTypes.setText(sb.toString());
                lonelySympthomPainScale.setText(lonelySympthom.getScale()+"");
            } else {
                lonelySympthomDescription.setText("");
                lonelySympthomPainTypes.setText("");
                lonelySympthomPainScale.setText("0");
            }
            randomizeColor(recordModel.getResourceNumber(), false);
        }
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
    public void randomizeColor(int position, boolean isRecyclerView) {

        Bitmap background = BitmapFactory.decodeResource(getResources(), getResourceId(position));

        //w tym momencie newBackgroud ma zmieniony kolor i mogę zabawić się paletą
        Palette.from(background).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette p) {

                int backgroundColor;
                int textColor;

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
                if(!isRecyclerView){
                    lonelySympthomDescriptionLayout.setBoxStrokeColor(backgroundColor);
                    lonelySympthomPainTypesLayout.setBoxStrokeColor(backgroundColor);
                    lonelySympthomPainScale.setTextColor(textColor);
                }
                descriptionLayout.setBoxStrokeColor(backgroundColor);
            }
        });
    }


}