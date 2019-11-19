package com.tekrevol.papp.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class DependantIdSendingModel {

    @Expose
    @SerializedName("dependent_id")
    private int dependent_id;

    public int getDependent_id() {
        return dependent_id;
    }

    public void setDependent_id(int dependent_id) {
        this.dependent_id = dependent_id;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
