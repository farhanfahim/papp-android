package com.android.structure.enums;

public enum CategoryType {
    MEDICALHISTORY,
    FAMILYHISTORY,
    SOCIALHISTORY,
    SMOKINGBEHAVIOR;

    public String canonicalForm() {
        return this.name();
    }

    public static CategoryType fromCanonicalForm(String canonical) {
        return (CategoryType) valueOf(CategoryType.class, canonical);
    }
}