package pages;

import java.sql.Date;

public class StaffConduct {
    private int conductId;
    private int staffId;
    private Date date;
    private String conductDescription;
    private String actionTaken;

    // Constructor
    public StaffConduct(int conductId, int staffId, Date date, String conductDescription, String actionTaken) {
        this.conductId = conductId;
        this.staffId = staffId;
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

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
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
