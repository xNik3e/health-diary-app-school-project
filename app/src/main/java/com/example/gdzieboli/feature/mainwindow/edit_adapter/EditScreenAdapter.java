package com.example.gdzieboli.feature.mainwindow.edit_adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Sympthom;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.zeeshan.material.multiselectionspinner.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditScreenAdapter extends RecyclerView.Adapter<EditScreenAdapter.ViewHolder> {

    Context context;
    List<Sympthom> sympthomList;

    public EditScreenAdapter(Context context, List<Sympthom> sympthomList) {
        this.context = context;
        this.sympthomList = sympthomList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.another_sympthoms_layout, parent,false);
        return new EditScreenAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sympthom sympthom = sympthomList.get(position);
        List<Integer> positionList = sympthom.getPositions();

        holder.sympthomName.setText(sympthom.getName());
        holder.description.setText(sympthom.getDescription());
        holder.slider.setValue(sympthom.getScale());
        holder.slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                slider.setLabelBehavior(LabelFormatter.LABEL_WITHIN_BOUNDS);
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                slider.setLabelBehavior(LabelFormatter.LABEL_GONE);
                sympthom.setScale((int) slider.getValue());
            }
        });

        List<String> list = populateList();
        holder.multiSelectionSpinner.setItems(list);
        if(!positionList.isEmpty()){
            for(int i : positionList){
                holder.multiSelectionSpinner.setSelection(list.get(i));
            }
        }
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sympthomName;
        TextInputEditText description;
        Slider slider;
        MultiSelectionSpinner multiSelectionSpinner;


        public ViewHolder(@NonNull View view) {
            super(view);

            sympthomName = view.findViewById(R.id.additional_organ_name);
            description = view.findViewById(R.id.sympthom_description);
            slider = view.findViewById(R.id.pain_slider);
            multiSelectionSpinner = view.findViewById(R.id.multi_selection_pain_type);

            description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    sympthomList.get(getAdapterPosition()).setDescription(description.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    sympthomList.get(getAdapterPosition()).setScale((int) slider.getValue());
                }
            });
            multiSelectionSpinner.setOnItemSelectedListener(new MultiSelectionSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, boolean isSelected, int position) {
                    if(isSelected){
                        sympthomList.get(getAdapterPosition()).addPosition(position);
                    }else{
                        sympthomList.get(getAdapterPosition()).removePosition(position);
                    }
                }
                @Override
                public void onSelectionCleared() {
                    sympthomList.get(getAdapterPosition()).getPositions().clear();
                }
            });

            description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                        hideKeyboard(v);
                }
            });

        }

        private List<Integer> translateEntirePosition(List<Object> positions) {
            return null;
        }

        public void hideKeyboard(View view) {
            InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }
}
