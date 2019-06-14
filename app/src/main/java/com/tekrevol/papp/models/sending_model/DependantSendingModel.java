package com.tekrevol.papp.models.sending_model;

import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DependantSendingModel {
    @Expose
    @SerializedName("gender")
    private int gender;
    @Expose
    @SerializedName("dob")
    private String dob;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    @SerializedName("first_name")
    private String firstName;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }

}
