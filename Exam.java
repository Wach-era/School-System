package pages;

import java.sql.Date;

public class Exam {
    private String examId;
    private String subjectName;
    private Date examDate;
    private double maxScore;
    private String classId;  // New field

    public Exam(String examId, String subjectName, Date examDate, double maxScore, String classId) {
        this.examId = examId;
        this.subjectName = subjectName;
        this.examDate = examDate;
        this.maxScore = maxScore;
        this.classId = classId;
    }

    // Getters and Setters
    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
