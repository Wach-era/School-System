package pages;

import java.sql.Date;

public class StudentFeePayment {
    private int paymentId;
    private int studentId;
    private String trimesterId;
    private double amountPaid;
    private Date dateOfPayment;
    private double overpayment;
    private double amountDue;
    private String paymentMode; 

    public StudentFeePayment() {}

    public StudentFeePayment(int studentId, String trimesterId, double amountPaid, Date dateOfPayment, double overpayment, double amountDue, String paymentMode) {
        this.studentId = studentId;
        this.trimesterId = trimesterId;
        this.amountPaid = amountPaid;
        this.dateOfPayment = dateOfPayment;
        this.overpayment = overpayment;
        this.amountDue = amountDue;
        this.paymentMode = paymentMode; 
    }

    public StudentFeePayment(int paymentId, int studentId, String trimesterId, double amountPaid, Date dateOfPayment, double overpayment, double amountDue, String paymentMode) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.trimesterId = trimesterId;
        this.amountPaid = amountPaid;
        this.dateOfPayment = dateOfPayment;
        this.overpayment = overpayment;
        this.amountDue = amountDue;
        this.paymentMode = paymentMode; 
    }
    
    public StudentFeePayment(int paymentId, int studentId, String trimesterId, double amountPaid, Date dateOfPayment, String paymentMode) {
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.trimesterId = trimesterId;
        this.amountPaid = amountPaid;
        this.dateOfPayment = dateOfPayment;
        this.paymentMode = paymentMode;
    }

    public StudentFeePayment(int studentId2, String trimesterId2, double amountDue2, Date date, double overpayment2,
			double amountDue3) {
		// TODO Auto-generated constructor stub
	}

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getTrimesterId() {
        return trimesterId;
    }

    public void setTrimesterId(String trimesterId) {
        this.trimesterId = trimesterId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public double getOverpayment() {
        return overpayment;
    }

    public void setOverpayment(double overpayment) {
        this.overpayment = overpayment;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }
}
