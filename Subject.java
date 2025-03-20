package pages;

public class Subject {
    private String subjectName;
    private String subjectCode;
    private String departmentId;
    private boolean isCompulsory;
    private String section;

    public Subject(String subjectName, String subjectCode, String departmentId, boolean isCompulsory, String section) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.departmentId = departmentId;
        this.isCompulsory = isCompulsory;
        this.section = section;
    }
    
    public Subject(String subjectName, String departmentId, boolean isCompulsory, String section) {
        this.subjectName = subjectName;
        this.departmentId = departmentId;
        this.isCompulsory = isCompulsory;
        this.section = section;
    }
    
    public Subject(String subjectName, String section) {
        this.subjectName = subjectName;
        this.section = section;
    }

    public Subject(String subjectName) {
    	 this.subjectName = subjectName;
    	 }

	public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isCompulsory() {
        return isCompulsory;
    }

    public void setCompulsory(boolean isCompulsory) {
        this.isCompulsory = isCompulsory;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
