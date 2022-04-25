package com.example.gdzieboli.feature.mainwindow.addMoreWindow.model;

public class Organ {
    private String name;
    private boolean isSelected = false;

    public Organ(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
