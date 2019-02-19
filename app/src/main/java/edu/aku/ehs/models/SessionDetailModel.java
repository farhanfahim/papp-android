package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.aku.ehs.enums.EmployeeSessionState;
import edu.aku.ehs.helperclasses.StringHelper;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SessionDetailModel {

    @Expose
    @SerializedName("LastFileUser")
    private String LastFileUser;
    @Expose
    @SerializedName("LastFileDTTM")
    private String LastFileDTTM;
    @Expose
    @SerializedName("CompletedBy")
    private String CompletedBy;
    @Expose
    @SerializedName("CompletedDTTM")
    private String CompletedDTTM;
    @Expose
    @SerializedName("DisplayCompletedDTTM")
    private String DisplayCompletedDTTM;
    @Expose
    @SerializedName("ScheduledBy")
    private String ScheduledBy;
    @Expose
    @SerializedName("ScheduledDTTM")
    private String ScheduledDTTM;
    @Expose
    @SerializedName("DisplayScheduledDTTM")
    private String DisplayScheduledDTTM;
    @Expose
    @SerializedName("EnrolledBy")
    private String EnrolledBy;
    @Expose
    @SerializedName("EnrolledDTTM")
    private String EnrolledDTTM;
    @Expose
    @SerializedName("DisplayEnrolledDTTM")
    private String DisplayEnrolledDTTM;
    @Expose
    @SerializedName("ReferredToDescription")
    private String ReferredToDescription;
    @Expose
    @SerializedName("ReferredToID")
    private String ReferredToID;
    @Expose
    @SerializedName("IsReferred")
    private String IsReferred;
    @Expose
    @SerializedName("HasMetabolicSyndrome")
    private String HasMetabolicSyndrome;
    @Expose
    @SerializedName("RiskScore")
    private String RiskScore;
    @Expose
    @SerializedName("DivisionName")
    private String DivisionName;
    @Expose
    @SerializedName("DivisionID")
    private String DivisionID;
    @Expose
    @SerializedName("DepartmentName")
    private String DepartmentName;
    @Expose
    @SerializedName("DepartmentID")
    private String DepartmentID;
    @Expose
    @SerializedName("HasLabResult")
    private String HasLabResult;
    @Expose
    @SerializedName("StatusID")
    private String StatusID;
    @Expose
    @SerializedName("Age")
    private String Age;
    @Expose
    @SerializedName("Gender")
    private String Gender;
    @Expose
    @SerializedName("EmployeeName")
    private String EmployeeName;
    @Expose
    @SerializedName("MedicalRecordNo")
    private String MedicalRecordNo;
    @Expose
    @SerializedName("EmployeeNo")
    private String EmployeeNo;
    @Expose
    @SerializedName("SessionID")
    private String SessionID;
    @Expose
    @SerializedName("ReferredToText")
    private String ReferredToText;

    private boolean isSelected = false;

    public SessionDetailModel() {
    }


    public EmployeeSessionState getStatusEnum() {
        return EmployeeSessionState.fromCanonicalForm(StatusID);
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getLastFileUser() {
        return LastFileUser;
    }

    public void setLastFileUser(String LastFileUser) {
        this.LastFileUser = LastFileUser;
    }

    public String getLastFileDTTM() {
        return LastFileDTTM;
    }

    public void setLastFileDTTM(String LastFileDTTM) {
        this.LastFileDTTM = LastFileDTTM;
    }

    public String getCompletedBy() {
        return CompletedBy;
    }

    public void setCompletedBy(String CompletedBy) {
        this.CompletedBy = CompletedBy;
    }

    public String getCompletedDTTM() {
        return CompletedDTTM;
    }

    public void setCompletedDTTM(String CompletedDTTM) {
        this.CompletedDTTM = CompletedDTTM;
    }

    public String getDisplayCompletedDTTM() {
        return DisplayCompletedDTTM;
    }

    public void setDisplayCompletedDTTM(String DisplayCompletedDTTM) {
        this.DisplayCompletedDTTM = DisplayCompletedDTTM;
    }

    public String getScheduledBy() {
        return ScheduledBy;
    }

    public void setScheduledBy(String ScheduledBy) {
        this.ScheduledBy = ScheduledBy;
    }

    public String getScheduledDTTM() {
        return ScheduledDTTM;
    }

    public void setScheduledDTTM(String ScheduledDTTM) {
        this.ScheduledDTTM = ScheduledDTTM;
    }

    public String getDisplayScheduledDTTM() {
        return DisplayScheduledDTTM;
    }

    public void setDisplayScheduledDTTM(String DisplayScheduledDTTM) {
        this.DisplayScheduledDTTM = DisplayScheduledDTTM;
    }

    public String getEnrolledBy() {
        return EnrolledBy;
    }

    public void setEnrolledBy(String EnrolledBy) {
        this.EnrolledBy = EnrolledBy;
    }

    public String getEnrolledDTTM() {
        return EnrolledDTTM;
    }

    public void setEnrolledDTTM(String EnrolledDTTM) {
        this.EnrolledDTTM = EnrolledDTTM;
    }

    public String getDisplayEnrolledDTTM() {
        return DisplayEnrolledDTTM;
    }

    public void setDisplayEnrolledDTTM(String DisplayEnrolledDTTM) {
        this.DisplayEnrolledDTTM = DisplayEnrolledDTTM;
    }

    public String getReferredToDescription() {
        return ReferredToDescription;
    }

    public void setReferredToDescription(String ReferredToDescription) {
        this.ReferredToDescription = ReferredToDescription;
    }

    public String getReferredToID() {
        return ReferredToID;
    }

    public void setReferredToID(String ReferredToID) {
        this.ReferredToID = ReferredToID;
    }

    public String getIsReferred() {
        return IsReferred;
    }

    public void setIsReferred(String IsReferred) {
        this.IsReferred = IsReferred;
    }

    public boolean getHasMetabolicSyndrome() {
        return StringHelper.checkNotNullAndNotEmpty(HasMetabolicSyndrome) && HasMetabolicSyndrome.equalsIgnoreCase("Y");

    }


    public void setHasMetabolicSyndrome(boolean HasMetabolicSyndrome) {
        if (HasMetabolicSyndrome) {
            this.HasMetabolicSyndrome = "Y";
        } else {
            this.HasMetabolicSyndrome = "N";
        }

    }


    public String getRiskScore() {
        return RiskScore;
    }

    public void setRiskScore(String RiskScore) {
        this.RiskScore = RiskScore;
    }

    public String getDivisionName() {
        return DivisionName;
    }

    public void setDivisionName(String DivisionName) {
        this.DivisionName = DivisionName;
    }

    public String getDivisionID() {
        return DivisionID;
    }

    public void setDivisionID(String DivisionID) {
        this.DivisionID = DivisionID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String DepartmentName) {
        this.DepartmentName = DepartmentName;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

    public String getHasLabResult() {
        return HasLabResult;
    }

    public boolean getisLabResultDone() {
        return StringHelper.checkNotNullAndNotEmpty(HasLabResult) && HasLabResult.equalsIgnoreCase("Y");
    }

    public void setHasLabResult(String HasLabResult) {
        this.HasLabResult = HasLabResult;
    }

    public String getStatusID() {
        return StatusID;
    }

    public void setStatusID(String StatusID) {
        this.StatusID = StatusID;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String EmployeeName) {
        this.EmployeeName = EmployeeName;
    }

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

    public String getReferredToText() {
        return ReferredToText;
    }

    public void setReferredToText(String referredToText) {
        ReferredToText = referredToText;
    }
}
