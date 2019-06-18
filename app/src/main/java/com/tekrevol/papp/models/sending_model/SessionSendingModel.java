package com.tekrevol.papp.models.sending_model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tekrevol.papp.managers.retrofit.GsonFactory;

import java.util.List;

public class SessionSendingModel {


    @Expose
    @SerializedName("session_user")
    private List<SessionUser> sessionUser;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("lat")
    private String lat;
    @Expose
    @SerializedName("lng")
    private String lng;
    @Expose
    @SerializedName("is_online")
    private int isOnline;
    @Expose
    @SerializedName("end_date")
    private String endDate;
    @Expose
    @SerializedName("start_date")
    private String startDate;
    @Expose
    @SerializedName("schedule_date")
    private String scheduleDate;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("session_type")
    private int sessionType;
    @Expose
    @SerializedName("mentor_id")
    private int mentorId;

    public List<SessionUser> getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(List<SessionUser> sessionUser) {
        this.sessionUser = sessionUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public static class SessionUser {
        @Expose
        @SerializedName("dependant_id")
        private int dependantId;

        public SessionUser() {
        }

        public SessionUser(int dependantId) {
            this.dependantId = dependantId;
        }

        public int getDependantId() {
            return dependantId;
        }

        public void setDependantId(int dependantId) {
            this.dependantId = dependantId;
        }
    }

    @Override
    public String toString() {
        return GsonFactory.getSimpleGson().toJson(this);
    }
}
