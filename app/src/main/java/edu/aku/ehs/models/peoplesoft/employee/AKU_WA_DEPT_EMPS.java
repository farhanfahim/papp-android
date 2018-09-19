package edu.aku.ehs.models.peoplesoft.employee;

        import org.simpleframework.xml.ElementList;

        import java.util.List;


public class AKU_WA_DEPT_EMPS {
    private String content;

    @ElementList(inline = true)
    private List<EMPLOYEE> EMPLOYEE;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<EMPLOYEE> getEMPLOYEE() {
        return EMPLOYEE;
    }

    public void setEMPLOYEE(List<EMPLOYEE> EMPLOYEE) {
        this.EMPLOYEE = EMPLOYEE;
    }

    @Override
    public String toString() {
        return "ClassPojo [content = " + content + ", EMPLOYEE = " + EMPLOYEE + "]";
    }
}