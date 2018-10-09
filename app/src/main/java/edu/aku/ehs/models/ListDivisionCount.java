package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListDivisionCount {
    @Expose
    @SerializedName("count_Employees")
    private int count_Employees;
    @Expose
    @SerializedName("DivisionName")
    private String DivisionName;
    @Expose
    @SerializedName("DivisionID")
    private String DivisionID;

    public int getCount_Employees() {
        return count_Employees;
    }

    public void setCount_Employees(int count_Employees) {
        this.count_Employees = count_Employees;
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
}
