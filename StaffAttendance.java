package pages;

import java.sql.Date;

public class StaffAttendance {
    private int attendanceId;
    private int staffId;
    private Date date;
    private String status;

    // Constructor
    public StaffAttendance(int attendanceId, int staffId, Date date, String status) {
        this.attendanceId = attendanceId;
        this.staffId = staffId;
        this.date = date;
        this.status = status;
    }

    // Getters and Setters
    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
