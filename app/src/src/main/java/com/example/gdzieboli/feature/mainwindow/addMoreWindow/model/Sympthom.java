package com.example.gdzieboli.feature.mainwindow.addMoreWindow.model;

import java.util.ArrayList;
import java.util.List;

public class Sympthom {
    private String name;
    private String description = "";
    private int scale = 1;
    private List<Integer> positions = new ArrayList<>();

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public Sympthom(String name) {
        this.name = name;
    }
    public void addPosition(int position){
        if(!positions.contains(Integer.valueOf(position))){
            positions.add(position);
        }
    }
    public void removePosition(int position){
        if(positions.contains(Integer.valueOf(position))){
            positions.remove(Integer.valueOf(position));
        }
    }


    public Sympthom(String name, String description, int scale, List<Integer> positions) {
        this.name = name;
        this.description = description;
        this.scale = scale;
        this.positions = positions;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
