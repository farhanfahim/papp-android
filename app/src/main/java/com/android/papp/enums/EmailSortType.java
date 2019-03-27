package com.android.papp.enums;

public enum EmailSortType {
    ALL,
    INCOMPLETE,
    COMPLETED;


    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static EmailSortType fromCanonicalForm(String canonical) {
        return (EmailSortType) valueOf(EmailSortType.class, canonical.toUpperCase());
    }
}