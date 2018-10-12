package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetabolicSyndromeRules {
    @Expose
    @SerializedName("IsTrue")
    private boolean IsTrue;
    @Expose
    @SerializedName("Rule")
    private String Rule;

    public boolean getIsTrue() {
        return IsTrue;
    }

    public void setIsTrue(boolean IsTrue) {
        this.IsTrue = IsTrue;
    }

    public String getRule() {
        return Rule;
    }

    public void setRule(String Rule) {
        this.Rule = Rule;
    }
}
