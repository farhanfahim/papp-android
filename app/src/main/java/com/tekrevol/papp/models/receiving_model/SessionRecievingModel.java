package com.tekrevol.papp.models.receiving_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class SessionRecievingModel {

    @Expose
    @SerializedName("mentor")
    private UserModel mentor;
    @Expose
    @SerializedName("user")
    private UserModel user;
    @Expose
    @SerializedName("session_users")
    private List<SessionUsers> sessionUsers;
    @Expose
    @SerializedName("formatted_schedule_date")
    private String formattedScheduleDate;
    @Expose
    @SerializedName("formatted_start_date")
    private String formattedStartDate;
    @Expose
    @SerializedName("online_status")
    private String onlineStatus;
    @Expose
    @SerializedName("type_text")
    private String typeText;
    @Expose
    @SerializedName("status_text")
    private String statusText;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
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
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("session_type")
    private int sessionType;
    @Expose
    @SerializedName("mentor_id")
    private int mentorId;
    @Expose
    @SerializedName("user_id")
    private int userId;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("duration")
    private int duration;


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public UserModel getMentor() {
        return mentor;
    }

    public void setMentor(UserModel mentor) {
        this.mentor = mentor;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<SessionUsers> getSessionUsers() {
        return sessionUsers;
    }

    public void setSessionUsers(List<SessionUsers> sessionUsers) {
        this.sessionUsers = sessionUsers;
    }

    public String getFormattedScheduleDate() {
        return formattedScheduleDate;
    }

    public void setFormattedScheduleDate(String formattedScheduleDate) {
        this.formattedScheduleDate = formattedScheduleDate;
    }

    public String getFormattedStartDate() {
        return formattedStartDate;
    }

    public void setFormattedStartDate(String formattedStartDate) {
        this.formattedStartDate = formattedStartDate;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public UserModel getDependent() {
        for (SessionUsers sessionUser : getSessionUsers()) {
            for (UserModel dependant : getUser().getDependants()) {
                if (dependant.getId() == sessionUser.getDependantId()) {
                    return dependant;
                }
            }
        }
        return null;
    }
}