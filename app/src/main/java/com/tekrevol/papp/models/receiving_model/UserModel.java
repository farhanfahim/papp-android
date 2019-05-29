package com.tekrevol.papp.models.receiving_model;

import com.tekrevol.papp.managers.retrofit.GsonFactory;
import com.tekrevol.papp.models.SpinnerModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserModel {


    @Expose
    @SerializedName("expires_in")
    private int expiresIn;
    @Expose
    @SerializedName("token_type")
    private String tokenType;
    @Expose
    @SerializedName("access_token")
    private String accessToken;
    @Expose
    @SerializedName("specialization")
    private List<SpinnerModel> specializations;
    @Expose
    @SerializedName("dependants")
    private List<UserModel> dependants;
    @Expose
    @SerializedName("details")
    private UserDetails userDetails;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("id")
    private int id;

    transient boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<SpinnerModel> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<SpinnerModel> specializations) {
        this.specializations = specializations;
    }

    public List<UserModel> getDependants() {
        return dependants;
    }

    public void setDependants(List<UserModel> dependants) {
        this.dependants = dependants;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}