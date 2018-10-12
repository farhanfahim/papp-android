package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CVDRiskDetail {
    @Expose
    @SerializedName("CVDRiskScore")
    private String CVDRiskScore;
    @Expose
    @SerializedName("isDiabetic")
    private String isDiabetic;
    @Expose
    @SerializedName("isSmoker")
    private String isSmoker;
    @Expose
    @SerializedName("value_SystolicBloodPressure")
    private String value_SystolicBloodPressure;
    @Expose
    @SerializedName("onBloodPressureTreatment")
    private boolean onBloodPressureTreatment;
    @Expose
    @SerializedName("value_TotalCholesterol")
    private String value_TotalCholesterol;
    @Expose
    @SerializedName("value_HDL")
    private String value_HDL;
    @Expose
    @SerializedName("patientGender")
    private String patientGender;
    @Expose
    @SerializedName("patientAge")
    private String patientAge;

    public String getCVDRiskScore() {
        return CVDRiskScore;
    }

    public void setCVDRiskScore(String CVDRiskScore) {
        this.CVDRiskScore = CVDRiskScore;
    }

    public String getIsDiabetic() {
        return isDiabetic;
    }

    public void setIsDiabetic(String isDiabetic) {
        this.isDiabetic = isDiabetic;
    }

    public String getIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(String isSmoker) {
        this.isSmoker = isSmoker;
    }

    public String getValue_SystolicBloodPressure() {
        return value_SystolicBloodPressure;
    }

    public void setValue_SystolicBloodPressure(String value_SystolicBloodPressure) {
        this.value_SystolicBloodPressure = value_SystolicBloodPressure;
    }

    public boolean getOnBloodPressureTreatment() {
        return onBloodPressureTreatment;
    }

    public void setOnBloodPressureTreatment(boolean onBloodPressureTreatment) {
        this.onBloodPressureTreatment = onBloodPressureTreatment;
    }

    public String getValue_TotalCholesterol() {
        return value_TotalCholesterol;
    }

    public void setValue_TotalCholesterol(String value_TotalCholesterol) {
        this.value_TotalCholesterol = value_TotalCholesterol;
    }

    public String getValue_HDL() {
        return value_HDL;
    }

    public void setValue_HDL(String value_HDL) {
        this.value_HDL = value_HDL;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }
}
