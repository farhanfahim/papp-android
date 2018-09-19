package edu.aku.ehs.models.peoplesoft.department;

import org.simpleframework.xml.ElementList;

import java.util.List;

public class AKU_WA_DEPT_EMPS {
    private String content;

    @ElementList(inline = true)
    private List<DEPT> DEPT;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<DEPT> getDEPT() {
        return DEPT;
    }

    public void setDEPT(List<DEPT> DEPT) {
        this.DEPT = DEPT;
    }

    @Override
    public String toString() {
        return "ClassPojo [content = " + content + ", DEPT = " + DEPT + "]";
    }
}