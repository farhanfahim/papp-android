package com.android.papp.models.sending_model;

import com.android.papp.managers.retrofit.GsonFactory;
import com.android.papp.models.receiving_model.Dependant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParentSendingModel {

    @Expose
    @SerializedName("dependant")
    private List<Dependant> dependant;
    @Expose
    @SerializedName("role")
    private int role;
    @Expose
    @SerializedName("device_type")
    private String deviceType;
    @Expose
    @SerializedName("device_token")
    private String deviceToken;
    @Expose
    @SerializedName("password_confirmation")
    private String passwordConfirmation;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    @SerializedName("first_name")
    private String firstName;


    public List<Dependant> getDependant() {
        return dependant;
    }

    public void setDependant(List<Dependant> dependant) {
        this.dependant = dependant;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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