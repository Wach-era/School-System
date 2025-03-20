package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_EXAMS_SQL = "INSERT INTO Exams (exam_id, subject_name, exam_date, max_score, class_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_EXAMS = "SELECT * FROM Exams";
    private static final String SELECT_EXAM_BY_ID = "SELECT exam_id, subject_name, exam_date, max_score, class_id FROM Exams WHERE exam_id = ?";
    private static final String UPDATE_EXAMS_SQL = "UPDATE Exams SET subject_name = ?, exam_date = ?, max_score = ?, class_id = ? WHERE exam_id = ?";
    private static final String DELETE_EXAM_SQL = "DELETE FROM Exams WHERE exam_id = ?";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertExam(Exam exam) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EXAMS_SQL)) {
            preparedStatement.setString(1, exam.getExamId());
            preparedStatement.setString(2, exam.getSubjectName());
            preparedStatement.setDate(3, exam.getExamDate());
            preparedStatement.setDouble(4, exam.getMaxScore());
            preparedStatement.setString(5, exam.getClassId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public List<Exam> listAllExams() {
        List<Exam> exams = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EXAMS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String examId = rs.getString("exam_id");
                String subjectName = rs.getString("subject_name");
                Date examDate = rs.getDate("exam_date");
                double maxScore = rs.getDouble("max_score");
                String classId = rs.getString("class_id");
                exams.add(new Exam(examId, subjectName, examDate, maxScore, classId));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exams;
    }

    public Exam selectExam(String examId) {
        Exam exam = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EXAM_BY_ID)) {
            preparedStatement.setString(1, examId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String subjectName = rs.getString("subject_name");
                Date examDate = rs.getDate("exam_date");
                double maxScore = rs.getDouble("max_score");
                String classId = rs.getString("class_id");
                exam = new Exam(examId, subjectName, examDate, maxScore, classId);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exam;
    }

    public boolean updateExam(Exam exam) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXAMS_SQL)) {
            preparedStatement.setString(1, exam.getSubjectName());
            preparedStatement.setDate(2, exam.getExamDate());
            preparedStatement.setDouble(3, exam.getMaxScore());
            preparedStatement.setString(4, exam.getClassId());
            preparedStatement.setString(5, exam.getExamId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean deleteExam(String examId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXAM_SQL)) {
            preparedStatement.setString(1, examId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    public boolean examExists(String examId) {
        String query = "SELECT 1 FROM Exams WHERE exam_id = ?";
        try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, examId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
