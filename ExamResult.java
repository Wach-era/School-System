package pages;

import java.sql.Date;

public class ExamResult {
    private int resultId;
    private String examId;
    private int studentId;
    private double score;
    private String subjectName;
    private Date examDate;
    private double maxScore;
    private Exam exam;
    private String firstName;
    private String middleName;
    private String lastName;


    // Constructors
    public ExamResult() {}

    public ExamResult(String examId, int studentId, double score) {
        this.examId = examId;
        this.studentId = studentId;
        this.score = score;
    }

    // Getters and Setters
    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
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
	    
	    public String getGrade() {
	        double percentage = (score / maxScore) * 100;
	        if (percentage >= 90) return "A";
	        else if (percentage >= 80) return "B";
	        else if (percentage >= 70) return "C";
	        else if (percentage >= 60) return "D";
	        else return "F";
	    }
}
