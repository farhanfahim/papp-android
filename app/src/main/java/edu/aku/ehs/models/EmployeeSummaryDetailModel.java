package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import edu.aku.ehs.helperclasses.StringHelper;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class EmployeeSummaryDetailModel {


    @Expose
    @SerializedName("HasMetabolicSyndrome")
    private String HasMetabolicSyndrome;
    @Expose
    @SerializedName("RiskScore")
    private String RiskScore;
    @Expose
    @SerializedName("RiskLevel")
    private String RiskLevel;
    @Expose
    @SerializedName("MetabolicSyndromeDetail")
    private MetabolicSyndromeDetail MetabolicSyndromeDetail;
    @Expose
    @SerializedName("CVDRiskDetail")
    private CVDRiskDetail CVDRiskDetail;
    @Expose
    @SerializedName("CVDRiskFactors")
    private List<CVDRiskFactors> CVDRiskFactors;
    @Expose
    @SerializedName("EmpLabs")
    private List<EmpLabs> EmpLabs;
    @Expose
    @SerializedName("EmpMeasurements")
    private ArrayList<ActiveMeasurementsModel> EmpMeasurements;
    @Expose
    @SerializedName("EmpAssessments")
    private ArrayList<AssessmentQuestionModel> EmpAssessments;

    public boolean isHasMetabolicSyndrome() {
        return StringHelper.checkNotNullAndNotEmpty(HasMetabolicSyndrome) && HasMetabolicSyndrome.equalsIgnoreCase("Y");
    }

    public ArrayList<ActiveMeasurementsModel> getEmpMeasurements() {
        return EmpMeasurements;
    }

    public void setEmpMeasurements(ArrayList<ActiveMeasurementsModel> empMeasurements) {
        EmpMeasurements = empMeasurements;
    }

    public ArrayList<AssessmentQuestionModel> getEmpAssessments() {
        return EmpAssessments;
    }

    public void setEmpAssessments(ArrayList<AssessmentQuestionModel> empAssessments) {
        EmpAssessments = empAssessments;
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

    public MetabolicSyndromeDetail getMetabolicSyndromeDetail() {
        return MetabolicSyndromeDetail;
    }

    public void setMetabolicSyndromeDetail(MetabolicSyndromeDetail MetabolicSyndromeDetail) {
        this.MetabolicSyndromeDetail = MetabolicSyndromeDetail;
    }

    public CVDRiskDetail getCVDRiskDetail() {
        return CVDRiskDetail;
    }

    public void setCVDRiskDetail(CVDRiskDetail CVDRiskDetail) {
        this.CVDRiskDetail = CVDRiskDetail;
    }

    public List<CVDRiskFactors> getCVDRiskFactors() {
        return CVDRiskFactors;
    }

    public void setCVDRiskFactors(List<CVDRiskFactors> CVDRiskFactors) {
        this.CVDRiskFactors = CVDRiskFactors;
    }

    public String getRiskLevel() {
        return RiskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        RiskLevel = riskLevel;
    }

    public List<EmpLabs> getEmpLabs() {
        return EmpLabs;
    }

    public void setEmpLabs(List<EmpLabs> EmpLabs) {
        this.EmpLabs = EmpLabs;
    }
}
