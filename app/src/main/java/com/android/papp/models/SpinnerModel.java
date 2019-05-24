package com.android.papp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hamza.ahmed on 3/16/2018.
 */

public class SpinnerModel {

    @Expose
    @SerializedName("name")
    private String text;
    @Expose
    @SerializedName("id")
    private int id = 0;


    private boolean isSelected = false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpinnerModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SpinnerModel) {
            return ((SpinnerModel) obj).text.equals(text);
        }
        return super.equals(obj);
    }

}
