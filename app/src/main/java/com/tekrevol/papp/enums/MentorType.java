package com.tekrevol.papp.enums;

public enum MentorType {
    MYMENTOR,
    TOPMENTOR,
    SEARCHMENTOR;


    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static MentorType fromCanonicalForm(String canonical) {
        return (MentorType) valueOf(MentorType.class, canonical.toUpperCase());
    }
}