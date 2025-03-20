package pages;

public class StudentClassEnrollment {
    private int enrollmentId;
    private int studentId;
    private String classId;
    private int yearId;
    private String status;
    private String studentName; // Add this field to store student name
    private boolean retake;

    // Full constructor
    public StudentClassEnrollment(int enrollmentId, int studentId, String classId, int yearId, String status, boolean retake) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.classId = classId;
        this.yearId = yearId;
        this.status = status;
        this.retake = retake;
    }

    // Constructor with studentId and studentName
    public StudentClassEnrollment(int studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    // Getters and Setters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    public boolean isRetake() {
        return retake;
    }

    public void setRetake(boolean retake) {
        this.retake = retake;
    }
}
