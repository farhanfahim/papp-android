package edu.aku.ehs.models.peoplesoft.employee;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import edu.aku.ehs.models.peoplesoft.employee.AKU_WA_DEPT_EMPS;
@Root
public class EmployeeWrapper
{   @Element
    private AKU_WA_DEPT_EMPS AKU_WA_DEPT_EMPS;

    public AKU_WA_DEPT_EMPS getAKU_WA_DEPT_EMPS ()
    {
        return AKU_WA_DEPT_EMPS;
    }

    public void setAKU_WA_DEPT_EMPS (AKU_WA_DEPT_EMPS AKU_WA_DEPT_EMPS)
    {
        this.AKU_WA_DEPT_EMPS = AKU_WA_DEPT_EMPS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [AKU_WA_DEPT_EMPS = "+AKU_WA_DEPT_EMPS+"]";
    }
}