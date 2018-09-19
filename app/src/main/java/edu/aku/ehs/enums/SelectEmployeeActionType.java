package edu.aku.ehs.enums;

public enum SelectEmployeeActionType {
    SENDEMAIL,
    ADDEMPLOYEE;

    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static SelectEmployeeActionType fromCanonicalForm(String canonical) {
        return (SelectEmployeeActionType) valueOf(SelectEmployeeActionType.class, canonical.toUpperCase());
    }
}