package pages;

import java.sql.Date;

public class StaffPayroll {
    private int payrollId;
    private int staffId;
    private String accountNumber;
    private double salary;
    private double bonus;
    private double deductions;
    private double netSalary;
    private Date paymentDate;
    private String status;
    private String fullName;

    
    public StaffPayroll(int payrollId, int staffId, String accountNumber, double salary, double bonus, double deductions, double netSalary, Date paymentDate, String status) {
        this.payrollId = payrollId;
        this.staffId = staffId;
        this.accountNumber = accountNumber;
        this.salary = salary;
        this.bonus = bonus;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentDate = paymentDate;
        this.status = status;
    }
    
    
    public StaffPayroll(int payrollId, int staffId,String fullName, String accountNumber, double salary, double bonus, double deductions, double netSalary, Date paymentDate, String status) {
        this.payrollId = payrollId;
        this.staffId = staffId;
        this.fullName = fullName;
        this.accountNumber = accountNumber;
        this.salary = salary;
        this.bonus = bonus;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and setters for each field
    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
