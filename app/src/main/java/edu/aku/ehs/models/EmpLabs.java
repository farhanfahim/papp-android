package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpLabs {
    @Expose
    @SerializedName("ResultComments")
    private String ResultComments;
    @Expose
    @SerializedName("Comments")
    private String Comments;
    @Expose
    @SerializedName("NormalRangeFormatted")
    private String NormalRangeFormatted;
    @Expose
    @SerializedName("AbnormalFlag")
    private String AbnormalFlag;
    @Expose
    @SerializedName("Unit")
    private String Unit;
    @Expose
    @SerializedName("Result")
    private String Result;
    @Expose
    @SerializedName("DisplaySpecimenDate")
    private String DisplaySpecimenDate;
    @Expose
    @SerializedName("SpecimenDate")
    private String SpecimenDate;
    @Expose
    @SerializedName("SpecimenNumber")
    private String SpecimenNumber;
    @Expose
    @SerializedName("TestID")
    private String TestID;
    @Expose
    @SerializedName("EmployeeNo")
    private String EmployeeNo;
    @Expose
    @SerializedName("SessionID")
    private String SessionID;

    public String getResultComments() {
        return ResultComments;
    }

    public void setResultComments(String ResultComments) {
        this.ResultComments = ResultComments;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public String getNormalRangeFormatted() {
        return NormalRangeFormatted;
    }

    public void setNormalRangeFormatted(String NormalRangeFormatted) {
        this.NormalRangeFormatted = NormalRangeFormatted;
    }

    public String getAbnormalFlag() {
        return AbnormalFlag;
    }

    public void setAbnormalFlag(String AbnormalFlag) {
        this.AbnormalFlag = AbnormalFlag;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getDisplaySpecimenDate() {
        return DisplaySpecimenDate;
    }

    public void setDisplaySpecimenDate(String DisplaySpecimenDate) {
        this.DisplaySpecimenDate = DisplaySpecimenDate;
    }

    public String getSpecimenDate() {
        return SpecimenDate;
    }

    public void setSpecimenDate(String SpecimenDate) {
        this.SpecimenDate = SpecimenDate;
    }

    public String getSpecimenNumber() {
        return SpecimenNumber;
    }

    public void setSpecimenNumber(String SpecimenNumber) {
        this.SpecimenNumber = SpecimenNumber;
    }

    public String getTestID() {
        return TestID;
    }

    public void setTestID(String TestID) {
        this.TestID = TestID;
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
}
