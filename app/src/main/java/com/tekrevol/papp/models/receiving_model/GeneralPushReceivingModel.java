package com.tekrevol.papp.models.receiving_model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

public class GeneralPushReceivingModel {


    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("sender_id")
    private int senderId;
    @Expose
    @SerializedName("action_type")
    private String actionType;
    @Expose
    @SerializedName("server_time")
    private ServerTime serverTime;
    @Expose
    @SerializedName("ref_id")
    private int refId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public ServerTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(ServerTime serverTime) {
        this.serverTime = serverTime;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

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

    @NonNull
    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
