package pages;

import java.sql.Date;

public class StudentConduct {
    private int conductId;
    private int studentId;
    private Date date;
    private String conductDescription;
    private String actionTaken;

    // Constructor
    public StudentConduct(int conductId, int studentId, Date date, String conductDescription, String actionTaken) {
        this.conductId = conductId;
        this.studentId = studentId;
        this.date = date;
        this.conductDescription = conductDescription;
        this.actionTaken = actionTaken;
    }

    // Getters and Setters
    public int getConductId() {
        return conductId;
    }

    public void setConductId(int conductId) {
        this.conductId = conductId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getConductDescription() {
        return conductDescription;
    }

    public void setConductDescription(String conductDescription) {
        this.conductDescription = conductDescription;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }
}
