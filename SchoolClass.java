package pages;

public class SchoolClass {
    private String classId;
    private int classTeacher;
    private int maxStudents;
    private String roomNumber;
    private String teacherName; // Add this field

    // Constructor for new classes without teacherName
    public SchoolClass(String classId, int classTeacher, int maxStudents, String roomNumber) {
        this.classId = classId;
        this.classTeacher = classTeacher;
        this.maxStudents = maxStudents;
        this.roomNumber = roomNumber;
    }

    // Constructor with teacherName
    public SchoolClass(String classId, int classTeacher, int maxStudents, String roomNumber, String teacherName) {
        this.classId = classId;
        this.classTeacher = classTeacher;
        this.maxStudents = maxStudents;
        this.roomNumber = roomNumber;
        this.teacherName = teacherName;
    }
    
    public SchoolClass(String classId) {
        this.classId = classId;
    }

    // Getters and Setters
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(int classTeacher) {
        this.classTeacher = classTeacher;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
