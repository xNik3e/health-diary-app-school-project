package com.example.gdzieboli.feature.mainwindow.addMoreWindow.model;

import java.util.Date;
import java.util.List;

public class RecordModel {
    private String MainOrgan;
    private List<String> additionalOrgans;
    private Date dateAdded;
    private String desciption;
    private List<String> sympthoms;
    private int id;
    private int resourceNumber;

    public int getResourceNumber() {
        return resourceNumber;
    }

    public void setResourceNumber(int resourceNumber) {
        this.resourceNumber = resourceNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RecordModel() {
    }

    public String getMainOrgan() {
        return MainOrgan;
    }

    public void setMainOrgan(String mainOrgan) {
        MainOrgan = mainOrgan;
    }

    public RecordModel(String mainOrgan, List<String> additionalOrgans, Date dateAdded) {
        MainOrgan = mainOrgan;
        this.additionalOrgans = additionalOrgans;
        this.dateAdded = dateAdded;
    }

    public List<String> getAdditionalOrgans() {
        return additionalOrgans;
    }

    public void setAdditionalOrgans(List<String> additionalOrgans) {
        this.additionalOrgans = additionalOrgans;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }


    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public List<String> getSympthoms() {
        return sympthoms;
    }

    public void setSympthoms(List<String> sympthoms) {
        this.sympthoms = sympthoms;
    }
}
