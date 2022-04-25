package com.example.gdzieboli.feature.mainwindow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gdzieboli.MainActivity;
import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Sympthom;
import com.example.gdzieboli.feature.mainwindow.edit_adapter.EditScreenAdapter;
import com.example.gdzieboli.utils.database.DbHelper;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.zeeshan.material.multiselectionspinner.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EditScreenActivity extends AppCompatActivity {
    private TextView mainOrgan;
    private RecyclerView additionalOrgans;
    private TextInputEditText description;
    private Button btn_save;
    private List<Sympthom> sympthomList = new ArrayList<>();
    private EditScreenAdapter adapter;
    private LinearLayout linearLayout;
    private boolean isRecyclerViewActive = false;
    private DbHelper dbHelper;
    private RecordModel recordModel;
    private Sympthom lonelySympthom;


    //IF NONE SELECTED

    private TextInputEditText descriptionText;
    private Slider slider;
    private MultiSelectionSpinner multiSelectionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        mainOrgan = findViewById(R.id.edit_main_organ);
        additionalOrgans = findViewById(R.id.additional_pain_organs);
        description = findViewById(R.id.edt_text_description);
        btn_save = findViewById(R.id.btn_apply);

        //IF NONE SELECTED

        descriptionText = findViewById(R.id.sympthom_description);
        slider = findViewById(R.id.pain_slider);
        multiSelectionSpinner = findViewById(R.id.multi_selection_pain_type);
        linearLayout = findViewById(R.id.linear_layout_empty_symptom);

        dbHelper = new DbHelper(this);

        boolean isEditData = getIntent().getBooleanExtra("isEditData", false);

        if (isEditData) {
            int recordID = getIntent().getIntExtra("recordID", 0);
            recordModel = dbHelper.getRecord(recordID + "");
        } else {
            String content = getIntent().getStringExtra("object");
            recordModel = (new Gson()).fromJson(content, RecordModel.class);
        }

        mainOrgan.setText(recordModel.getMainOrgan());
        description.setText(recordModel.getDesciption());


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

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

            additionalOrgans.setLayoutManager(linearLayoutManager);
            adapter = new EditScreenAdapter(this, sympthomList);

            additionalOrgans.setAdapter(adapter);
            additionalOrgans.setVisibility(View.VISIBLE);
            isRecyclerViewActive = true;
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            additionalOrgans.setVisibility(View.GONE);
            isRecyclerViewActive = false;
            if (recordModel.getSympthoms() != null && !recordModel.getSympthoms().isEmpty()) {
                lonelySympthom = (new Gson()).fromJson(recordModel.getSympthoms().get(0), Sympthom.class);
            } else {
                lonelySympthom = new Sympthom("NONE");
            }
            descriptionText.setText(lonelySympthom.getDescription());
            slider.setValue(lonelySympthom.getScale());
            List<String> items = populateList();
            multiSelectionSpinner.setItems(items);
            for (Integer i : lonelySympthom.getPositions()) {
                multiSelectionSpinner.setSelection(items.get(i));
            }


            slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onStartTrackingTouch(@NonNull Slider slider) {
                    slider.setLabelBehavior(LabelFormatter.LABEL_WITHIN_BOUNDS);
                }

                @SuppressLint("RestrictedApi")
                @Override
                public void onStopTrackingTouch(@NonNull Slider slider) {
                    slider.setLabelBehavior(LabelFormatter.LABEL_GONE);
                }
            });

            multiSelectionSpinner.setOnItemSelectedListener(new MultiSelectionSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, boolean isSelected, int position) {
                    if (isSelected) {
                        lonelySympthom.addPosition(position);
                    } else {
                        lonelySympthom.removePosition(position);
                    }
                }

                @Override
                public void onSelectionCleared() {
                    lonelySympthom.getPositions().clear();
                }
            });

            descriptionText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                        hideKeyboard(v);
                }
            });

        }

        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                    hideKeyboard(v);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> sympthoms = new ArrayList<>();
                recordModel.setDesciption(description.getText().toString());
                if (isRecyclerViewActive) {
                    for (Sympthom s : sympthomList) {
                        String content = (new Gson()).toJson(s);
                        sympthoms.add(content);
                    }

                } else {//if recyclerview isnt visible
                    Sympthom s = new Sympthom("NONE");
                    if (descriptionText.getText().length() > 0) {
                        s.setDescription(descriptionText.getText().toString());
                    } else {
                        s.setDescription("");
                    }
                    s.setScale((int) slider.getValue());
                    s.setPositions(lonelySympthom.getPositions());
                    sympthoms.add((new Gson()).toJson(s));
                }
                recordModel.setSympthoms(sympthoms);

                if (isEditData) {
                    recordModel.setId(getIntent().getIntExtra("recordID", 0));
                    if (dbHelper.updateRecord(recordModel) == 1) {
                        Toast.makeText(EditScreenActivity.this, "Pomyślnie zaaktualizowano wpis", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditScreenActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    if (dbHelper.insertRecord(recordModel) != -1) {
                        Toast.makeText(EditScreenActivity.this, "Pomyślnie dodano nowy wpis", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditScreenActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditScreenActivity.this, "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

}