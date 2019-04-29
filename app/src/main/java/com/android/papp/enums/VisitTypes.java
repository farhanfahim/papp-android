package com.android.papp.enums;

public enum VisitTypes {
    ER,
    SDC,
    OUT,
    INP;


    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static VisitTypes fromCanonicalForm(String canonical) {
        return (VisitTypes) valueOf(VisitTypes.class, canonical.toUpperCase());
    }
}