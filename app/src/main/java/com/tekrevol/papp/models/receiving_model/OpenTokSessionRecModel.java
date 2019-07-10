package com.tekrevol.papp.models.receiving_model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class OpenTokSessionRecModel {

    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("api_key")
    private String apiKey;
    @Expose
    @SerializedName("otk_session_id")
    private String otkSessionId;

    private boolean isCaller;
    private int sessionType;


    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public boolean isCaller() {
        return isCaller;
    }

    public void setCaller(boolean caller) {
        isCaller = caller;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getOtkSessionId() {
        return otkSessionId;
    }

    public void setOtkSessionId(String otkSessionId) {
        this.otkSessionId = otkSessionId;
    }

    @NonNull
    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
