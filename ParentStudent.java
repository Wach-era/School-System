package pages;

public class ParentStudent {
    private int parentId;
    private int studentId;
    private String parentName; // Fix: Only one name field

    public ParentStudent(int parentId, int studentId, String parentName) {
        this.parentId = parentId;
        this.studentId = studentId;
        this.parentName = parentName;
    }

    public ParentStudent(int parentId, int studentId) {
        this.parentId = parentId;
        this.studentId = studentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getParentName() {
        return parentName;
    }
}
