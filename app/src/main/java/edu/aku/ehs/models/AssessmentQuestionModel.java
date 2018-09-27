package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.aku.ehs.enums.CategoryType;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class AssessmentQuestionModel {

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
    @SerializedName("AskIsUnderTreatment")
    private String AskIsUnderTreatment;
    @Expose
    @SerializedName("CategoryID")
    private String CategoryID;
    @Expose
    @SerializedName("QuestionID")
    private String QuestionID;
    @Expose
    @SerializedName("CategoryDescription")
    private String CategoryDescription;
    @Expose
    @SerializedName("QuestionDescription")
    private String QuestionDescription;
    @Expose
    @SerializedName("IsUnderTreatment")
    private String IsUnderTreatment;
    @Expose
    @SerializedName("IsAnswerYes")
    private String IsAnswerYes;
    @Expose
    @SerializedName("EmployeeNo")
    private String EmployeeNo;
    @Expose
    @SerializedName("SessionID")
    private String SessionID;

    private CategoryType categoryType;


    public boolean isAnswer1() {
        return getIsAnswerYes() != null && getIsAnswerYes().equalsIgnoreCase("Y");
    }

    public void setAnswer1(boolean answer1) {
        if (answer1) {
            this.setIsAnswerYes("Y");
        } else {
            this.setIsAnswerYes("N");
        }
    }

    public boolean isAnswer2() {
        return getIsUnderTreatment() != null && getIsUnderTreatment().equals("Y");
    }

    public void setAnswer2(boolean answer2) {
        if (answer2) {
            setIsUnderTreatment("Y");
        } else {
            setIsUnderTreatment("N");
        }
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

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

    public String getAskIsUnderTreatment() {
        return AskIsUnderTreatment;
    }

    public void setAskIsUnderTreatment(String AskIsUnderTreatment) {
        this.AskIsUnderTreatment = AskIsUnderTreatment;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String CategoryID) {
        this.CategoryID = CategoryID;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String QuestionID) {
        this.QuestionID = QuestionID;
    }

    public String getCategoryDescription() {
        return CategoryDescription;
    }

    public void setCategoryDescription(String CategoryDescription) {
        this.CategoryDescription = CategoryDescription;
    }

    public String getQuestionDescription() {
        return QuestionDescription;
    }

    public void setQuestionDescription(String QuestionDescription) {
        this.QuestionDescription = QuestionDescription;
    }

    public String getIsUnderTreatment() {
        return IsUnderTreatment;
    }

    public void setIsUnderTreatment(String IsUnderTreatment) {
        this.IsUnderTreatment = IsUnderTreatment;
    }

    public String getIsAnswerYes() {
        return IsAnswerYes;
    }

    public void setIsAnswerYes(String IsAnswerYes) {
        this.IsAnswerYes = IsAnswerYes;
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
