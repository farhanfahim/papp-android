package edu.aku.ehs.models.sending_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.aku.ehs.managers.retrofit.GsonFactory;

public class SearchSendingModel {


    @Expose
    @SerializedName("MedicalRecordNo")
    private String MedicalRecordNo;
    @Expose
    @SerializedName("EmployeeNo")
    private String EmployeeNo;
    @Expose
    @SerializedName("SessionID")
    private String SessionID;

    public String getMedicalRecordNo() {
        return MedicalRecordNo;
    }

    public void setMedicalRecordNo(String MedicalRecordNo) {
        this.MedicalRecordNo = MedicalRecordNo;
    }

    public String getEmployeeNo() {
        return EmployeeNo;
    }

    public void setEmployeeNo(String EmployeeNo) {
        this.EmployeeNo = EmployeeNo;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }


    @Override
    public String toString() {
        return GsonFactory.getConfiguredGson().toJson(this);
    }

}
