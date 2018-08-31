package edu.aku.ehs.models;

import edu.aku.ehs.enums.EmployeeAssessmentState;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class EmployeeAssessmentModel {

    private String assessmentName;
    private String date;
    private EmployeeAssessmentState employeeAssessmentState;


    public EmployeeAssessmentModel(String assessmentName, String date, EmployeeAssessmentState employeeAssessmentState) {
        this.assessmentName = assessmentName;
        this.date = date;
        this.employeeAssessmentState = employeeAssessmentState;
    }


    public EmployeeAssessmentState getEmployeeAssessmentState() {
        return employeeAssessmentState;
    }

    public void setEmployeeAssessmentState(EmployeeAssessmentState employeeAssessmentState) {
        this.employeeAssessmentState = employeeAssessmentState;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public String getDate() {
        return date;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
