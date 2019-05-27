package com.tekrevol.papp.enums;

public enum LeaType {
    MYLEA,
    TOPLEA;


    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static LeaType fromCanonicalForm(String canonical) {
        return (LeaType) valueOf(LeaType.class, canonical.toUpperCase());
    }
}