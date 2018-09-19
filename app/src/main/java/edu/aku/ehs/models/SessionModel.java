package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.aku.ehs.managers.retrofit.GsonFactory;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SessionModel {


    @Expose
    @SerializedName("Count_LabCompleted")
    private int countLabCompleted;
    @Expose
    @SerializedName("Count_Assessed")
    private int countAssessed;
    @Expose
    @SerializedName("Count_InProgress")
    private int countInprogress;
    @Expose
    @SerializedName("Count_Enrolled")
    private int countEnrolled;
    @Expose
    @SerializedName("EndDate")
    private String endDate;
    @Expose
    @SerializedName("StartDate")
    private String startDate;
    @Expose
    @SerializedName("DisplayEndDate")
    private String displayEndDate;
    @Expose
    @SerializedName("DisplayStartDate")
    private String displayStartDate;
    @Expose
    @SerializedName("StatusID")
    private String statusId;
    @Expose
    @SerializedName("Description")
    private String description;
    @Expose
    @SerializedName("SessionID")
    private String sessionId;
    @Expose
    @SerializedName("Active")
    private String active;
    @Expose
    @SerializedName("LastFileUser")
    private String lastFileUser;
    @Expose
    @SerializedName("ClosedBy")
    private String closedBy;


    public String getDisplayEndDate() {
        return displayEndDate;
    }

    public void setDisplayEndDate(String displayEndDate) {
        this.displayEndDate = displayEndDate;
    }

    public String getDisplayStartDate() {
        return displayStartDate;
    }

    public void setDisplayStartDate(String displayStartDate) {
        this.displayStartDate = displayStartDate;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getLastFileUser() {
        return lastFileUser;
    }

    public void setLastFileUser(String lastFileUser) {
        this.lastFileUser = lastFileUser;
    }

    public int getCountLabCompleted() {
        return countLabCompleted;
    }

    public void setCountLabCompleted(int countLabCompleted) {
        this.countLabCompleted = countLabCompleted;
    }

    public int getCountAssessed() {
        return countAssessed;
    }

    public void setCountAssessed(int countAssessed) {
        this.countAssessed = countAssessed;
    }

    public int getCountInprogress() {
        return countInprogress;
    }

    public void setCountInprogress(int countInprogress) {
        this.countInprogress = countInprogress;
    }

    public int getCountEnrolled() {
        return countEnrolled;
    }

    public void setCountEnrolled(int countEnrolled) {
        this.countEnrolled = countEnrolled;
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

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    @Override
    public String toString() {
        return GsonFactory.getConfiguredGson().toJson(this);
    }
}
