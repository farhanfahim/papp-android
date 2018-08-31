package edu.aku.ehs.enums;

public enum QuestionTypeEnum {
    MEDICALHISTORY,
    FAMILYHISTORY,
    PSYCHOSOCIALHISTORY,
    SMOKINGBEHAVIOR;

    public String canonicalForm() {
        return this.name().toLowerCase();
    }

    public static QuestionTypeEnum fromCanonicalForm(String canonical) {
        return (QuestionTypeEnum) valueOf(QuestionTypeEnum.class, canonical.toUpperCase());
    }
}