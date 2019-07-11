package com.tekrevol.papp.models.receiving_model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class OpenTokSessionRecModel {


    @Expose
    @SerializedName("ref_id")
    private int refId;
    @Expose
    @SerializedName("sender_id")
    private int senderId;
    @Expose
    @SerializedName("action_type")
    private String actionType;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("mentor_name")
    private String mentorName;
    @Expose
    @SerializedName("session_type")
    private String sessionType;
    @Expose
    @SerializedName("otk_session_id")
    private String otkSessionId;
    @Expose
    @SerializedName("api_key")
    private String apiKey;
    @Expose
    @SerializedName("mentor_image")
    private String mentorImage;
    @Expose
    @SerializedName("server_time")
    private ServerTime serverTime;

    private boolean isCaller;


    public static class ServerTime {
        @Expose
        @SerializedName("timezone_type")
        private int timezoneType;
        @Expose
        @SerializedName("timezone")
        private String timezone;
        @Expose
        @SerializedName("date")
        private String date;

        public int getTimezoneType() {
            return timezoneType;
        }

        public void setTimezoneType(int timezoneType) {
            this.timezoneType = timezoneType;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }


    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getOtkSessionId() {
        return otkSessionId;
    }

    public void setOtkSessionId(String otkSessionId) {
        this.otkSessionId = otkSessionId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMentorImage() {
        return mentorImage;
    }

    public void setMentorImage(String mentorImage) {
        this.mentorImage = mentorImage;
    }

    public ServerTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(ServerTime serverTime) {
        this.serverTime = serverTime;
    }

    public boolean isCaller() {
        return isCaller;
    }

    public void setCaller(boolean caller) {
        isCaller = caller;
    }

    @NonNull
    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
