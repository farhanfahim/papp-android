package com.tekrevol.papp.enums;

public enum SearchByType {
    MRNUMBER,
    EMPLOYEENUMBER,
    DEPARTMENT;

    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static SearchByType fromCanonicalForm(String canonical) {
        return (SearchByType) valueOf(SearchByType.class, canonical.toUpperCase());
    }
}