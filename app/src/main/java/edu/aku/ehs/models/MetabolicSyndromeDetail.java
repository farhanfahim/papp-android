package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetabolicSyndromeDetail {

    @Expose
    @SerializedName("MetabolicSyndromeRules")
    private List<MetabolicSyndromeRules> MetabolicSyndromeRules;
    @Expose
    @SerializedName("HasMetabolicSyndrome")
    private boolean HasMetabolicSyndrome;
    @Expose
    @SerializedName("onDiabetesTreatment")
    private boolean onDiabetesTreatment;
    @Expose
    @SerializedName("value_FastingGlucose")
    private String value_FastingGlucose;
    @Expose
    @SerializedName("onBloodPressureTreatment")
    private boolean onBloodPressureTreatment;
    @Expose
    @SerializedName("value_DiastolicBloodPressure")
    private String value_DiastolicBloodPressure;
    @Expose
    @SerializedName("value_SystolicBloodPressure")
    private String value_SystolicBloodPressure;
    @Expose
    @SerializedName("value_HDL")
    private String value_HDL;
    @Expose
    @SerializedName("onCholesterolTreatement")
    private boolean onCholesterolTreatement;
    @Expose
    @SerializedName("value_TriGlyceride")
    private String value_TriGlyceride;
    @Expose
    @SerializedName("value_WaistCircumference")
    private String value_WaistCircumference;
    @Expose
    @SerializedName("patientGender")
    private String patientGender;

    public List<MetabolicSyndromeRules> getMetabolicSyndromeRules() {
        return MetabolicSyndromeRules;
    }

    public void setMetabolicSyndromeRules(List<MetabolicSyndromeRules> MetabolicSyndromeRules) {
        this.MetabolicSyndromeRules = MetabolicSyndromeRules;
    }

    public boolean getHasMetabolicSyndrome() {
        return HasMetabolicSyndrome;
    }

    public void setHasMetabolicSyndrome(boolean HasMetabolicSyndrome) {
        this.HasMetabolicSyndrome = HasMetabolicSyndrome;
    }

    public boolean getOnDiabetesTreatment() {
        return onDiabetesTreatment;
    }

    public void setOnDiabetesTreatment(boolean onDiabetesTreatment) {
        this.onDiabetesTreatment = onDiabetesTreatment;
    }

    public String getValue_FastingGlucose() {
        return value_FastingGlucose;
    }

    public void setValue_FastingGlucose(String value_FastingGlucose) {
        this.value_FastingGlucose = value_FastingGlucose;
    }

    public boolean getOnBloodPressureTreatment() {
        return onBloodPressureTreatment;
    }

    public void setOnBloodPressureTreatment(boolean onBloodPressureTreatment) {
        this.onBloodPressureTreatment = onBloodPressureTreatment;
    }

    public String getValue_DiastolicBloodPressure() {
        return value_DiastolicBloodPressure;
    }

    public void setValue_DiastolicBloodPressure(String value_DiastolicBloodPressure) {
        this.value_DiastolicBloodPressure = value_DiastolicBloodPressure;
    }

    public String getValue_SystolicBloodPressure() {
        return value_SystolicBloodPressure;
    }

    public void setValue_SystolicBloodPressure(String value_SystolicBloodPressure) {
        this.value_SystolicBloodPressure = value_SystolicBloodPressure;
    }

    public String getValue_HDL() {
        return value_HDL;
    }

    public void setValue_HDL(String value_HDL) {
        this.value_HDL = value_HDL;
    }

    public boolean getOnCholesterolTreatement() {
        return onCholesterolTreatement;
    }

    public void setOnCholesterolTreatement(boolean onCholesterolTreatement) {
        this.onCholesterolTreatement = onCholesterolTreatement;
    }

    public String getValue_TriGlyceride() {
        return value_TriGlyceride;
    }

    public void setValue_TriGlyceride(String value_TriGlyceride) {
        this.value_TriGlyceride = value_TriGlyceride;
    }

    public String getValue_WaistCircumference() {
        return value_WaistCircumference;
    }

    public void setValue_WaistCircumference(String value_WaistCircumference) {
        this.value_WaistCircumference = value_WaistCircumference;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }
}
