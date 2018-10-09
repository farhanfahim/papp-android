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
        if (StringHelper.checkNotNullAndNotEmpty(AKU_MRNO)) {
            isSelected = selected;
        } else {
            isSelected = false;
        }
    }

    @Override
    public String toString() {
        return "ClassPojo [EMPL_RCD = " + EMPL_RCD + ", EMAIL_ADDR = " + EMAIL_ADDR + ", HR_STATUS = " + HR_STATUS + ", NAME = " + NAME + ", GRADE = " + GRADE + ", EMPLID = " + EMPLID + ", AKU_MRNO = " + AKU_MRNO + ", EXT = " + EXT + ", DEPTID = " + DEPTID + ", DESCR = " + DESCR + ", TITLE = " + TITLE + "]";
    }
}