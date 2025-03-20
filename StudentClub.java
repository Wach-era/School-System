package pages;

import java.time.LocalDate;

public class StudentClub {
    private int studentClubId;
    private int studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String clubName;
    private LocalDate joinDate;

    // Full constructor
    public StudentClub(int studentClubId, int studentId, String firstName, String middleName, String lastName, String clubName, LocalDate joinDate) {
        this.studentClubId = studentClubId;
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.clubName = clubName;
        this.joinDate = joinDate;
    }
    
    public StudentClub(int studentClubId, int studentId, String clubName, LocalDate joinDate) {
        this.studentClubId = studentClubId;
        this.studentId = studentId;
        this.clubName = clubName;
        this.joinDate = joinDate;
    }

    // Getters and setters
    public int getStudentClubId() {
        return studentClubId;
    }

    public void setStudentClubId(int studentClubId) {
        this.studentClubId = studentClubId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }
}
