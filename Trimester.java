package pages;

import java.sql.Date;

public class Trimester {
    private String trimesterId;
    private Date startDate;
    private Date endDate;

    public Trimester() {}

    public Trimester(String trimesterId, Date startDate, Date endDate) {
        this.trimesterId = trimesterId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTrimesterId() {
        return trimesterId;
    }

    public void setTrimesterId(String trimesterId) {
        this.trimesterId = trimesterId;
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
