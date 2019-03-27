package com.android.structure.enums;

public enum CVDRiskFactorTypes {
    AGE,
    HDL,
    TOTALCHOL,
    SBPTREATED,
    SBPNOTTREATED,
    SMOKER,
    DIABETIC;

    public String canonicalForm() {
        return this.name();
    }

    public static CVDRiskFactorTypes fromCanonicalForm(String canonical) {
        return (CVDRiskFactorTypes) valueOf(CVDRiskFactorTypes.class, canonical);
    }
}