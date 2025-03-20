package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamResultDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public ExamResultDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addResult(ExamResult result) throws SQLException {
        String query = "INSERT INTO ExamResults (exam_id, student_id, score) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, result.getExamId());
            pstmt.setInt(2, result.getStudentId());
            pstmt.setDouble(3, result.getScore());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error adding exam result", e);
        }
    }

    public List<ExamResult> getResultsByExamId(String examId) throws SQLException {
        String query = "SELECT * FROM ExamResults WHERE exam_id = ?";
        List<ExamResult> results = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, examId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ExamResult result = new ExamResult();
                result.setResultId(rs.getInt("result_id"));
                result.setExamId(rs.getString("exam_id"));
                result.setStudentId(rs.getInt("student_id"));
                result.setScore(rs.getDouble("score"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching exam results", e);
        }
        return results;
    }
    
    public void updateResult(ExamResult result) throws SQLException {
        String query = "UPDATE ExamResults SET score = ? WHERE result_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDouble(1, result.getScore());
            pstmt.setInt(2, result.getResultId());
            pstmt.executeUpdate();
        }
    }

    public void deleteResult(int resultId) throws SQLException {
        String query = "DELETE FROM ExamResults WHERE result_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, resultId);
            pstmt.executeUpdate();
        }
    }


    public List<Student> getStudentsByExamId(String examId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT s.student_id, s.first_name, s.last_name " +
                "FROM Students s " +
                "JOIN StudentSubjects ss ON s.student_id = ss.student_id " +
                "JOIN Exams e ON ss.subject_name = e.subject_name " +
                "JOIN StudentClassEnrollment sce ON s.student_id = sce.student_id " +
                "WHERE e.exam_id = ? AND e.class_id = sce.class_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, examId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                students.add(new Student(studentId, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching students for the exam ID: " + examId, e);
        }
        return students;
    }
    
    public double getMaxScore(String examId) throws SQLException {
        String sql = "SELECT max_score FROM Exams WHERE exam_id = ?";
        try (//Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, examId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("max_score");
            } else {
                throw new SQLException("Exam not found");
            }
        }
    }

    public boolean studentHasResult(String examId, int studentId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ExamResults WHERE exam_id = ? AND student_id = ?";
        try (//Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, examId);
            statement.setInt(2, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        }
    }


//    private String getClassIdByExamId(String examId) throws SQLException {
//        String query = "SELECT class_id FROM Exams WHERE exam_id = ?";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, examId);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                return rs.getString("class_id");
//            } else {
//                throw new SQLException("Class ID not found for exam ID: " + examId);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new SQLException("Error fetching class ID for exam ID: " + examId, e);
//        }
//    }
}
