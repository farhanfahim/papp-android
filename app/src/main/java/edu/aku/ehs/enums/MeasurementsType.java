package edu.aku.ehs.enums;

public enum MeasurementsType {
    WAIST,
    SBP,
    DBP,
    BMI,
    HEIGHT,
    WEIGHT;

    public String canonicalForm() {
        return this.name();
    }

    public static MeasurementsType fromCanonicalForm(String canonical) {
        return (MeasurementsType) valueOf(MeasurementsType.class, canonical);
    }
}