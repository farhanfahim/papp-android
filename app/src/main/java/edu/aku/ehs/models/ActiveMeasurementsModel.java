package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.aku.ehs.enums.MeasurementsType;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class ActiveMeasurementsModel {


    @Expose
    @SerializedName("LastFileUser")
    private String LastFileUser;
    @Expose
    @SerializedName("LastFileDateTime")
    private String LastFileDateTime;
    @Expose
    @SerializedName("Description")
    private String Description;
    @Expose
    @SerializedName("Active")
    private String Active;
    @Expose
    @SerializedName("NormalWomenMax")
    private int NormalWomenMax;
    @Expose
    @SerializedName("NormalMenMax")
    private int NormalMenMax;


    @Expose
    @SerializedName("NormalWomenMin")
    private int NormalWomenMin;
    @Expose
    @SerializedName("NormalMenMin")
    private int NormalMenMin;

    @Expose
    @SerializedName("UnitofMeasure")
    private String UnitofMeasure;
    @Expose
    @SerializedName("MaxRange")
    private int MaxRange;
    @Expose
    @SerializedName("MinRange")
    private int MinRange;
    @Expose
    @SerializedName("MeasurementID")
    private String MeasurementID;
    @Expose
    @SerializedName("SessionID")
    private String SessionID;
    @Expose
    @SerializedName("EmployeeNo")
    private String EmployeeNo;
    @Expose
    @SerializedName("Value")
    private String Value;

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public String getEmployeeNo() {
        return EmployeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        EmployeeNo = employeeNo;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public MeasurementsType getMeasurementsType() {
        return measurementsType;
    }

    public void setMeasurementsType(MeasurementsType measurementsType) {
        this.measurementsType = measurementsType;
    }

    private MeasurementsType measurementsType;


    public String getLastFileUser() {
        return LastFileUser;
    }

    public void setLastFileUser(String LastFileUser) {
        this.LastFileUser = LastFileUser;
    }

    public String getLastFileDateTime() {
        return LastFileDateTime;
    }

    public void setLastFileDateTime(String LastFileDateTime) {
        this.LastFileDateTime = LastFileDateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String Active) {
        this.Active = Active;
    }

    public int getNormalWomenMax() {
        return NormalWomenMax;
    }

    public void setNormalWomenMax(int NormalWomenMax) {
        this.NormalWomenMax = NormalWomenMax;
    }

    public int getNormalMenMax() {
        return NormalMenMax;
    }

    public void setNormalMenMax(int NormalMenMax) {
        this.NormalMenMax = NormalMenMax;
    }

    public String getUnitofMeasure() {
        return UnitofMeasure;
    }

    public void setUnitofMeasure(String UnitofMeasure) {
        this.UnitofMeasure = UnitofMeasure;
    }

    public int getMaxRange() {
        return MaxRange;
    }

    public void setMaxRange(int MaxRange) {
        this.MaxRange = MaxRange;
    }

    public int getMinRange() {
        return MinRange;
    }

    public void setMinRange(int MinRange) {
        this.MinRange = MinRange;
    }

    public String getMeasurementID() {
        return MeasurementID;
    }

    public void setMeasurementID(String MeasurementID) {
        this.MeasurementID = MeasurementID;
    }

    public int getNormalWomenMin() {
        return NormalWomenMin;
    }

    public void setNormalWomenMin(int normalWomenMin) {
        NormalWomenMin = normalWomenMin;
    }

    public int getNormalMenMin() {
        return NormalMenMin;
    }

    public void setNormalMenMin(int normalMenMin) {
        NormalMenMin = normalMenMin;
    }
}
