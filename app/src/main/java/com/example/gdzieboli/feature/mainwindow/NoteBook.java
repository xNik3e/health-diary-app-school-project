package com.example.gdzieboli.feature.mainwindow;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.feature.mainwindow.notebookAdapter.NoteBookAdapter;
import com.example.gdzieboli.utils.database.DbHelper;

import java.util.List;

public class NoteBook extends Fragment implements NoteBookAdapter.DeleteRecord {


    private DbHelper dbHelper;
    private TextView nothing_here;
    private RecyclerView recyclerView;
    private Context context;
    private NoteBookAdapter noteBookAdapter;
    private List<RecordModel> recordModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_book, container, false);
        context = getContext();
        dbHelper = new DbHelper(context);

        nothing_here = view.findViewById(R.id.text_nothing_here);
        recyclerView = view.findViewById(R.id.recyclerview_notebook);

        recordModels = dbHelper.getRecords();
        if (recordModels.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            nothing_here.setVisibility(View.VISIBLE);
        } else {
            nothing_here.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            noteBookAdapter = new NoteBookAdapter(context, recordModels, this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(noteBookAdapter);
        }

        return view;
    }


    @Override
    public void deleteR(int position, int recordId) {
        if(dbHelper.deleteRecord(recordId) == 1){
            Toast.makeText(context, "Pomyślnie usunięto wpis!", Toast.LENGTH_SHORT).show();
            recordModels.remove(position);
            noteBookAdapter.notifyItemRemoved(position);
            if(recordModels.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                nothing_here.setVisibility(View.VISIBLE);
            }
        }
    }

}