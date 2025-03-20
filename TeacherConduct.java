package pages;

import java.sql.Date;

public class TeacherConduct {
    private int conductId;
    private int nationalId;
    private Date date;
    private String conductDescription;
    private String actionTaken;

    // Constructor
    public TeacherConduct(int conductId, int nationalId, Date date, String conductDescription, String actionTaken) {
        this.conductId = conductId;
        this.nationalId = nationalId;
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

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
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
