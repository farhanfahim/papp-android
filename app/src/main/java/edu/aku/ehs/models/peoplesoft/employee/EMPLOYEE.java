package edu.aku.ehs.models.peoplesoft.employee;

import edu.aku.ehs.helperclasses.StringHelper;

public class EMPLOYEE {
    private String EMPL_RCD;

    private String EMAIL_ADDR;

    private String HR_STATUS;

    private String NAME;

    private String GRADE;

    private String EMPLID;

    private String AKU_MRNO;

    private String EXT;

    private String DEPTID;

    private String DESCR;

    private String TITLE;

    private String FULLTIME_PARTTIME;

    private String FUTURE_TERM_DT;

    private String GENDER;

    private String BIRTHDATE;

    public String getEMPL_RCD() {
        return EMPL_RCD;
    }

    public void setEMPL_RCD(String EMPL_RCD) {
        this.EMPL_RCD = EMPL_RCD;
    }

    public String getEMAIL_ADDR() {
        return EMAIL_ADDR;
    }

    public void setEMAIL_ADDR(String EMAIL_ADDR) {
        this.EMAIL_ADDR = EMAIL_ADDR;
    }

    public String getHR_STATUS() {
        return HR_STATUS;
    }

    public void setHR_STATUS(String HR_STATUS) {
        this.HR_STATUS = HR_STATUS;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getEMPLID() {
        return EMPLID;
    }

    public void setEMPLID(String EMPLID) {
        this.EMPLID = EMPLID;
    }

    public String getAKU_MRNO() {
        return AKU_MRNO;
    }

    public void setAKU_MRNO(String AKU_MRNO) {
        this.AKU_MRNO = AKU_MRNO;
    }

    public String getEXT() {
        return EXT;
    }

    public void setEXT(String EXT) {
        this.EXT = EXT;
    }

    public String getDEPTID() {
        return DEPTID;
    }

    public void setDEPTID(String DEPTID) {
        this.DEPTID = DEPTID;
    }

    public String getDESCR() {
        return DESCR;
    }

    public void setDESCR(String DESCR) {
        this.DESCR = DESCR;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }


    private transient boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        if (StringHelper.checkNotNullAndNotEmpty(AKU_MRNO)  && StringHelper.isNullOrEmpty(FUTURE_TERM_DT)) {
            isSelected = selected;
        } else {
            isSelected = false;
        }
    }


    public String getFULLTIME_PARTTIMEDESC() {
        if (StringHelper.isNullOrEmpty(FULLTIME_PARTTIME)) {
            return "";
        } else {
            return FULLTIME_PARTTIME.equalsIgnoreCase("F") ? "Full-time" : "Part-time";
        }
    }


    public String getFULLTIME_PARTTIME() {
        return FULLTIME_PARTTIME == null ? "" : FULLTIME_PARTTIME;
    }

    public void setFULLTIME_PARTTIME(String FULLTIME_PARTTIME) {
        this.FULLTIME_PARTTIME = FULLTIME_PARTTIME;
    }

    public String getFUTURE_TERM_DT() {
        return FUTURE_TERM_DT;
    }

    public void setFUTURE_TERM_DT(String FUTURE_TERM_DT) {
        this.FUTURE_TERM_DT = FUTURE_TERM_DT;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getBIRTHDATE() {
        return BIRTHDATE;
    }

    public void setBIRTHDATE(String BIRTHDATE) {
        this.BIRTHDATE = BIRTHDATE;
    }

    @Override
    public String toString() {
        return "ClassPojo [EMPL_RCD = " + EMPL_RCD + ", EMAIL_ADDR = " + EMAIL_ADDR + ", HR_STATUS = " + HR_STATUS + ", NAME = " + NAME + ", GRADE = " + GRADE + ", EMPLID = " + EMPLID + ", AKU_MRNO = " + AKU_MRNO + ", EXT = " + EXT + ", DEPTID = " + DEPTID + ", DESCR = " + DESCR + ", TITLE = " + TITLE + "]";
    }
}