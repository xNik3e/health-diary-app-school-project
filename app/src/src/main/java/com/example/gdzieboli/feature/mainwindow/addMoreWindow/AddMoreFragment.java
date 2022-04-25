package com.example.gdzieboli.feature.mainwindow.addMoreWindow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.EditScreenActivity;
import com.example.gdzieboli.feature.mainwindow.NoteBook;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.Organ;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.utils.database.DbHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AddMoreFragment extends Fragment {

    private List<Organ> organs = new ArrayList<>();
    private Context context;
    private RecyclerView organsRecyclerView;
    private OrganAdapter organAdapter;
    private Button button;
    private RecordModel recordModel;
    private int rId;
    private DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_more, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.dbHelper = new DbHelper(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d("TEST", "CZY DZIALA?");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        organsRecyclerView = view.findViewById(R.id.recycler_view_fragment_add_more);
        rId = getArguments().getInt("resourceId");
        organs = getListData(rId);

        organAdapter = new OrganAdapter(context, organs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

        organsRecyclerView.setLayoutManager(linearLayoutManager);
        organsRecyclerView.setAdapter(organAdapter);

        button = view.findViewById(R.id.submit_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isSelected = 0;
                List<Organ> tempOrgans = new ArrayList<>();
                for (Organ o : organs) {
                    if (o.isSelected()) {
                        isSelected += 1;
                        tempOrgans.add(o);
                    }
                }
                RecordModel recordModel = new RecordModel();
                String mainOrgan = "";
                switch (rId) {
                    case R.drawable.upper_head_clicked:
                        mainOrgan = "Górna część głowy";
                        break;
                    case R.drawable.lower_head_clicked:
                        mainOrgan = "Dolna część głowy";
                        break;
                    case R.drawable.chest_clicked:
                        mainOrgan = "Klatka piersiowa";
                        break;
                    case R.drawable.belly_clicked:
                        mainOrgan = "Brzuch";
                        break;
                    case R.drawable.left_arm_clicked:
                        mainOrgan = "Lewa ręka";
                        break;
                    case R.drawable.right_arm_clicked:
                        mainOrgan = "Prawa ręka";
                        break;
                    case R.drawable.left_leg_clicked:
                        mainOrgan = "Lewa noga";
                        break;
                    case R.drawable.right_leg_clicked:
                        mainOrgan = "Prawa noga";
                        break;
                }
                recordModel.setMainOrgan(mainOrgan);
                recordModel.setDateAdded(new Date(System.currentTimeMillis()));
                recordModel.setDesciption("");
                recordModel.setSympthoms(null);

                Random rnd = new Random();
                recordModel.setResourceNumber(rnd.nextInt(48));

                if (isSelected == 0) {
                    recordModel.setAdditionalOrgans(null);
                } else {
                    List<String> list = new ArrayList<>();
                    for (Organ o : tempOrgans) {
                        list.add(o.getName());
                    }
                    recordModel.setAdditionalOrgans(list);
                }

                Intent intent = new Intent(getActivity().getBaseContext(), EditScreenActivity.class);

                String content = (new Gson()).toJson(recordModel);
                intent.putExtra("object", content);
                getActivity().startActivity(intent);

            }
        });

    }


    private List<Organ> getListData(int resourceId) {
        List<Organ> organs = new ArrayList<>();

        switch (resourceId) {
            case R.drawable.upper_head_clicked:
                organs.add(new Organ("Czoło"));
                organs.add(new Organ("Skroń"));
                organs.add(new Organ("Zatoki"));
                organs.add(new Organ("Oko"));
                break;
            case R.drawable.lower_head_clicked:
                organs.add(new Organ("Ucho"));
                organs.add(new Organ("Ząb"));
                organs.add(new Organ("Gardło"));
                organs.add(new Organ("Szyja"));
                break;
            case R.drawable.chest_clicked:
                organs.add(new Organ("Prawa strona"));
                organs.add(new Organ("Lewa strona"));
                organs.add(new Organ("Plecy"));
                organs.add(new Organ("Łopatka"));
                organs.add(new Organ("Kark"));
                break;
            case R.drawable.belly_clicked:
                organs.add(new Organ("Prawa strona"));
                organs.add(new Organ("Lewa strona"));
                organs.add(new Organ("Odcinek lędźwiowy"));
                organs.add(new Organ("Odbyt"));
                organs.add(new Organ("Narządy moczowe"));
                organs.add(new Organ("Krok"));
                break;
            case R.drawable.right_leg_clicked:
            case R.drawable.left_leg_clicked:
                organs.add(new Organ("Udo"));
                organs.add(new Organ("Kolano"));
                organs.add(new Organ("Łydka"));
                organs.add(new Organ("Stopa"));
                break;
            case R.drawable.right_arm_clicked:
            case R.drawable.left_arm_clicked:
                organs.add(new Organ("Bark"));
                organs.add(new Organ("Ramię"));
                organs.add(new Organ("Przedramię"));
                organs.add(new Organ("Dłoń"));
                break;
        }
        return organs;
    }
}