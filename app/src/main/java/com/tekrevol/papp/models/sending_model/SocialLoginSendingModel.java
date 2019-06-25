package com.tekrevol.papp.models.sending_model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class SocialLoginSendingModel {


    @Expose
    @SerializedName("device_type")
    private String deviceType;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("client_id")
    private String clientId;
    @Expose
    @SerializedName("platform")
    private String platform;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
