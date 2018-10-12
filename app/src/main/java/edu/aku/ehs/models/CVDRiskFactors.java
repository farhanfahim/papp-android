package edu.aku.ehs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import edu.aku.ehs.enums.CVDRiskFactorTypes;

public class CVDRiskFactors {
    @Expose
    @SerializedName("POINTS")
    private int POINTS;
    @Expose
    @SerializedName("VALUE")
    private String VALUE;
    @Expose
    @SerializedName("ISNUMERIC")
    private String ISNUMERIC;
    @Expose
    @SerializedName("DESCRIPTION")
    private String DESCRIPTION;
    @Expose
    @SerializedName("ACTIVE")
    private String ACTIVE;
    @Expose
    @SerializedName("FACTORID")
    private String FACTORID;

    CVDRiskFactorTypes cvdRiskFactorTypes;

    public CVDRiskFactorTypes getCvdRiskFactorTypes() {
        return cvdRiskFactorTypes;
    }

    public void setCvdRiskFactorTypes(CVDRiskFactorTypes cvdRiskFactorTypes) {
        this.cvdRiskFactorTypes = cvdRiskFactorTypes;
    }

    public int getPOINTS() {
        return POINTS;
    }

    public void setPOINTS(int POINTS) {
        this.POINTS = POINTS;
    }

    public String getVALUE() {
        return VALUE;
    }

    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

    public String getISNUMERIC() {
        return ISNUMERIC;
    }

    public void setISNUMERIC(String ISNUMERIC) {
        this.ISNUMERIC = ISNUMERIC;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public String getFACTORID() {
        return FACTORID;
    }

    public void setFACTORID(String FACTORID) {
        this.FACTORID = FACTORID;
    }
}
