package pages;

import java.sql.Date;

public class AcademicYear {
    private int yearId;
    private Date startDate;
    private Date endDate;

    public AcademicYear(int yearId, Date startDate, Date endDate) {
        this.yearId = yearId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
