package com.tekrevol.papp.models.wrappers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.models.receiving_model.UserModel;

public class DependentDetailWrapper {
    @Expose
    @SerializedName("dependent")
    private UserModel dependent;
   @Expose
    @SerializedName("parent")
    private UserModel parent;


    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
