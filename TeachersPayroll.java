package pages;

import java.sql.Date;

public class TeachersPayroll {
    private int payrollId;
    private int nationalId;
    private double salary;
    private double bonus;
    private double deductions;
    private double netSalary;
    private Date paymentDate;
    private String status;
    private String teacherFullName;
	private String fullName;


    public TeachersPayroll() {
    }

    public TeachersPayroll(int payrollId, int nationalId, double salary, double bonus, double deductions, double netSalary, Date paymentDate, String status) {
        this.payrollId = payrollId;
        this.nationalId = nationalId;
        this.salary = salary;
        this.bonus = bonus;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentDate = paymentDate;
        this.status = status;
    }
    
    public TeachersPayroll(int payrollId, int nationalId, String fullName, double salary, double bonus, double deductions, double netSalary, Date paymentDate, String status) {
        this.payrollId = payrollId;
        this.nationalId = nationalId;
        this.fullName = fullName;
        this.salary = salary;
        this.bonus = bonus;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getDeductions() {
        return deductions;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
