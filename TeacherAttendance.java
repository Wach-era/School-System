package pages;

import java.sql.Date;

public class TeacherAttendance {
    private int attendanceId;
    private int nationalId;
    private Date date;
    private String status;

    // Constructor
    public TeacherAttendance(int attendanceId, int nationalId, Date date, String status) {
        this.attendanceId = attendanceId;
        this.nationalId = nationalId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
