package com.tekrevol.papp.enums;

public enum EmployeeAssessmentState {
    INPROGRESS,
    COMPLETED;

    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static EmployeeAssessmentState fromCanonicalForm(String canonical) {
        return (EmployeeAssessmentState) valueOf(EmployeeAssessmentState.class, canonical.toUpperCase());
    }
}