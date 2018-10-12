package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListDepartmentCount {
    @Expose
    @SerializedName("count_Employees")
    private int count_Employees;
    @Expose
    @SerializedName("DepartmentName")
    private String DepartmentName;
    @Expose
    @SerializedName("DepartmentID")
    private String DepartmentID;

    public int getCount_Employees() {
        return count_Employees;
    }

    public void setCount_Employees(int count_Employees) {
        this.count_Employees = count_Employees;
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
}
