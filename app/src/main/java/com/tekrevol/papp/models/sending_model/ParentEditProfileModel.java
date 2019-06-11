package com.tekrevol.papp.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

import java.util.List;

public class ParentEditProfileModel {

    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    @SerializedName("first_name")
    private String firstName;


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