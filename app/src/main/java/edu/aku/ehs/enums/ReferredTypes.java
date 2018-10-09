package edu.aku.ehs.enums;

public enum ReferredTypes {
    CHC,
    IMS,
    OTHER;

    public String canonicalForm() {
        return this.name();
    }

    public static ReferredTypes fromCanonicalForm(String canonical) {
        return (ReferredTypes) valueOf(ReferredTypes.class, canonical);
    }
}