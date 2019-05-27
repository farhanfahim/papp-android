package com.tekrevol.papp.models.wrappers;

import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.models.receiving_model.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.models.receiving_model.UserModel;

public class UserModelWrapper {
    @Expose
    @SerializedName("user")
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
