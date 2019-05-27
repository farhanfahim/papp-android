package com.tekrevol.papp.enums;

public enum SessionStatus {
    OPENED,
    CLOSED;

    public String canonicalForm() {
        return this.name().toUpperCase();
    }

    public static SessionStatus fromCanonicalForm(String canonical) {
        return (SessionStatus) valueOf(SessionStatus.class, canonical.toUpperCase());
    }
}