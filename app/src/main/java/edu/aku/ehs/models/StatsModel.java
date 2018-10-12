package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatsModel {


    @Expose
    @SerializedName("Count_PsychosocialHistory")
    private int Count_PsychosocialHistory;
    @Expose
    @SerializedName("Count_Anemia")
    private int Count_Anemia;
    @Expose
    @SerializedName("Count_HepC")
    private int Count_HepC;
    @Expose
    @SerializedName("Count_Referred")
    private int Count_Referred;
    @Expose
    @SerializedName("Count_MetabolicSyndrome")
    private int Count_MetabolicSyndrome;
    @Expose
    @SerializedName("ListDepartmentCount")
    private List<ListDepartmentCount> ListDepartmentCount;
    @Expose
    @SerializedName("ListDivisionCount")
    private List<ListDivisionCount> ListDivisionCount;
    @Expose
    @SerializedName("TotalEmployee")
    private int TotalEmployee;

    public int getCount_PsychosocialHistory() {
        return Count_PsychosocialHistory;
    }

    public void setCount_PsychosocialHistory(int Count_PsychosocialHistory) {
        this.Count_PsychosocialHistory = Count_PsychosocialHistory;
    }

    public int getCount_Anemia() {
        return Count_Anemia;
    }

    public void setCount_Anemia(int Count_Anemia) {
        this.Count_Anemia = Count_Anemia;
    }

    public int getCount_HepC() {
        return Count_HepC;
    }

    public void setCount_HepC(int Count_HepC) {
        this.Count_HepC = Count_HepC;
    }

    public int getCount_Referred() {
        return Count_Referred;
    }

    public void setCount_Referred(int Count_Referred) {
        this.Count_Referred = Count_Referred;
    }

    public int getCount_MetabolicSyndrome() {
        return Count_MetabolicSyndrome;
    }

    public void setCount_MetabolicSyndrome(int Count_MetabolicSyndrome) {
        this.Count_MetabolicSyndrome = Count_MetabolicSyndrome;
    }

    public List<ListDepartmentCount> getListDepartmentCount() {
        return ListDepartmentCount;
    }

    public void setListDepartmentCount(List<ListDepartmentCount> ListDepartmentCount) {
        this.ListDepartmentCount = ListDepartmentCount;
    }

    public List<ListDivisionCount> getListDivisionCount() {
        return ListDivisionCount;
    }

    public void setListDivisionCount(List<ListDivisionCount> ListDivisionCount) {
        this.ListDivisionCount = ListDivisionCount;
    }

    public int getTotalEmployee() {
        return TotalEmployee;
    }

    public void setTotalEmployee(int TotalEmployee) {
        this.TotalEmployee = TotalEmployee;
    }
}
