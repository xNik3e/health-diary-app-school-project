package com.example.gdzieboli.feature.mainwindow;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gdzieboli.R;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.AddMoreFragment;
import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;
import com.example.gdzieboli.utils.database.DbHelper;
import com.example.gdzieboli.utils.picker.ColorTool;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Date;
import java.util.Random;


public class PickerFragment extends Fragment {

    ImageView body, colorMap;
    ExtendedFloatingActionButton submit, addMore;
    Animation fabOpenAnim, fabCloseAnim;
    boolean isShownFab = false, isAnotherFragmentShown = false;
    FrameLayout darkenView, container;
    AddMoreFragment addMoreFragment = null;
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);
        dbHelper = new DbHelper(getContext());
        body = view.findViewById(R.id.image_body);
        colorMap = view.findViewById(R.id.image_color_map);
        submit = view.findViewById(R.id.fab_submit);
        addMore = view.findViewById(R.id.fab_add_more);
        fabOpenAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabCloseAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        darkenView = view.findViewById(R.id.darken_view);
        container = view.findViewById(R.id.fragment_container_picker);

        ViewGroup finalContainer = container;
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                final int action = ev.getAction();
                // (1)
                final int evX = (int) ev.getX();
                final int evY = (int) ev.getY();
                Integer tagNum = (Integer) body.getTag();
                int nextImage = -1;
                int currentResource = (tagNum == null) ? R.drawable.body : tagNum.intValue();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (isAnotherFragmentShown) {
                            FragmentManager fm = getChildFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            transaction.setCustomAnimations(R.anim.slide_from_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_from_down);
                            addMoreFragment = (AddMoreFragment) fm.findFragmentByTag("addMoreFragment");


                            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_from_up);
                            finalContainer.bringToFront();
                            finalContainer.startAnimation(animation);

                            transaction.remove(addMoreFragment).commit();

                            changeVisibility(isAnotherFragmentShown);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        int touchColor = getHotspotColor(R.id.image_color_map, evX, evY);
                        ColorTool ct = new ColorTool();
                        int tolerance = 25;
                        if (ct.closeMatch(0xff0000, touchColor, tolerance)) {
                            nextImage = R.drawable.upper_head_clicked;
                        } else if (ct.closeMatch(0xfb00ff, touchColor, tolerance)) {
                            nextImage = R.drawable.lower_head_clicked;
                        } else if (ct.closeMatch(0x5400ff, touchColor, tolerance)) {
                            nextImage = R.drawable.chest_clicked;
                        } else if (ct.closeMatch(0x2eff00, touchColor, tolerance)) {
                            nextImage = R.drawable.belly_clicked;
                        } else if (ct.closeMatch(0xffec00, touchColor, tolerance)) {
                            nextImage = R.drawable.right_leg_clicked;
                        } else if (ct.closeMatch(0xff7200, touchColor, tolerance)) {
                            nextImage = R.drawable.left_leg_clicked;
                        } else if (ct.closeMatch(0x4db974, touchColor, tolerance)) {
                            nextImage = R.drawable.right_arm_clicked;
                        } else if (ct.closeMatch(0x8476c3, touchColor, tolerance)) {
                            nextImage = R.drawable.left_arm_clicked;
                        } else {
                            nextImage = R.drawable.body;
                        }
                        break;
                } // end switch
                if (nextImage > 0) {
                    body.setImageResource(nextImage);
                    body.setTag(nextImage);
                    if (nextImage != R.drawable.body && !isShownFab) {
                        submit.setVisibility(View.VISIBLE);
                        addMore.setVisibility(View.VISIBLE);
                        submit.startAnimation(fabOpenAnim);
                        addMore.startAnimation(fabOpenAnim);
                        isShownFab = true;
                    } else if (nextImage == R.drawable.body && isShownFab) {
                        submit.startAnimation(fabCloseAnim);
                        addMore.startAnimation(fabCloseAnim);
                        submit.setVisibility(View.GONE);
                        addMore.setVisibility(View.GONE);
                        isShownFab = false;
                    }
                }
                return true;
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("resourceId", (Integer) body.getTag());
                submit.startAnimation(fabCloseAnim);
                addMore.startAnimation(fabCloseAnim);
                submit.setVisibility(View.GONE);
                addMore.setVisibility(View.GONE);
                isShownFab = false;


                addMoreFragment = new AddMoreFragment();
                addMoreFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_from_down)
                        .replace(R.id.fragment_container_picker, addMoreFragment, "addMoreFragment")
                        .addToBackStack("tag")
                        .commit();
                changeVisibility(isAnotherFragmentShown);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainOrgan = findOrganByTag((int)body.getTag());
                RecordModel recordModel = new RecordModel();
                recordModel.setMainOrgan(mainOrgan);
                recordModel.setDateAdded(new Date(System.currentTimeMillis()));
                recordModel.setDesciption("");
                recordModel.setSympthoms(null);
                recordModel.setAdditionalOrgans(null);
                Random rnd = new Random();
                recordModel.setResourceNumber(rnd.nextInt(48));

                if(dbHelper.insertRecord(recordModel) != -1){
                    submit.startAnimation(fabCloseAnim);
                    addMore.startAnimation(fabCloseAnim);
                    submit.setVisibility(View.GONE);
                    addMore.setVisibility(View.GONE);
                    isShownFab = false;
                    Toast.makeText(getContext(), "Pomyślnie dodano nowy wpis", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Coś poszło nie tak :c", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    private String findOrganByTag(int tag) {
        switch (tag) {
            case R.drawable.upper_head_clicked:
                return "Górna część głowy";
            case R.drawable.lower_head_clicked:
                return "Dolna część głowy";
            case R.drawable.chest_clicked:
                return "Klatka piersiowa";
            case R.drawable.belly_clicked:
                return "Brzuch";
            case R.drawable.left_arm_clicked:
                return "Lewa ręka";
            case R.drawable.right_arm_clicked:
                return "Prawa ręka";
            case R.drawable.left_leg_clicked:
                return "Lewa noga";
            case R.drawable.right_leg_clicked:
                return "Prawa noga";
            default:
                return null;
        }
    }


    //Metody pomocnicze
    public void changeVisibility(boolean isVisible) {
        int colorFrom, colorTo;
        if (isVisible) {
            colorFrom = getResources().getColor(R.color.darken);
            colorTo = getResources().getColor(R.color.transparent);
        } else {
            colorTo = getResources().getColor(R.color.darken);
            colorFrom = getResources().getColor(R.color.transparent);
        }
        isAnotherFragmentShown = !isVisible;
        ValueAnimator colorAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimator.setDuration(1000);
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                darkenView.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimator.start();
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) getView().findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }


}