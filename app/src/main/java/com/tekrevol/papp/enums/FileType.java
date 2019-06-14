package com.tekrevol.papp.enums;

public enum FileType {
    IMAGE,
    VIDEO,
    APPLICATION,
    DOCUMENT,;


    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static FileType fromCanonicalForm(String canonical) {
        return (FileType) valueOf(FileType.class, canonical.toUpperCase());
    }
}