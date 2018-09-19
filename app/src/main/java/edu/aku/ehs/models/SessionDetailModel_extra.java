package edu.aku.ehs.models;

import edu.aku.ehs.enums.EmployeeSessionState;

/**
 * Created by hamza.ahmed on 7/23/2018.
 */

public class SessionDetailModel_extra {

    private String employeeName;
    private String date;
    private EmployeeSessionState status;
    private boolean isSelected = false;
    private boolean inSelectionMode = false;


    public boolean isInSelectionMode() {
        return inSelectionMode;
    }

    public void setInSelectionMode(boolean inSelectionMode) {
        this.inSelectionMode = inSelectionMode;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public SessionDetailModel_extra(String sessionName, EmployeeSessionState status) {
        this.employeeName = sessionName;
        this.status = status;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public EmployeeSessionState getStatus() {
        return status;
    }

    public void setStatus(EmployeeSessionState status) {
        this.status = status;
    }
}
