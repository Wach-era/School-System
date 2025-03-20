package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TranscriptDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";
    
    private static final String SELECT_ALL_CLASSES = "SELECT class_id FROM Classes";
    private static final String SELECT_STUDENT_RESULTS_BY_TRIMESTER = 
        "SELECT e.subject_name, er.student_id, er.score, e.max_score, s.first_name, s.middle_name, s.last_name " +
        "FROM ExamResults er " +
        "JOIN Exams e ON er.exam_id = e.exam_id " +
        "JOIN Students s ON er.student_id = s.student_id " +
        "JOIN Trimesters t ON e.exam_date BETWEEN t.start_date AND t.end_date " +
        "WHERE er.student_id = ? AND t.trimester_id = ? " +
        "ORDER BY e.subject_name";
    
    public TranscriptDAO() {
        this.connection = getConnection();
    }
    
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

    public List<Trimester> getAllTrimesters() throws SQLException {
        String query = "SELECT * FROM Trimesters";
        List<Trimester> trimesters = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Trimester trimester = new Trimester();
                trimester.setTrimesterId(resultSet.getString("trimester_id"));
                trimester.setStartDate(resultSet.getDate("start_date"));
                trimester.setEndDate(resultSet.getDate("end_date"));
                trimesters.add(trimester);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return trimesters;
    }

    public List<SchoolClass> selectAllClasses() {
        List<SchoolClass> classes = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLASSES)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String classId = rs.getString("class_id");
                classes.add(new SchoolClass(classId));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return classes;
    }

    public List<ExamResult> getResultsByClassAndTrimester(String classId, String trimesterId) throws SQLException {
        String query = "SELECT e.subject_name, er.student_id, er.score, e.max_score " +
                       "FROM ExamResults er " +
                       "JOIN Exams e ON er.exam_id = e.exam_id " +
                       "JOIN StudentClassEnrollment sce ON er.student_id = sce.student_id " +
                       "JOIN Trimesters t ON e.exam_date BETWEEN t.start_date AND t.end_date " +
                       "WHERE sce.class_id = ? AND t.trimester_id = ? " +
                       "ORDER BY e.subject_name, e.exam_date";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, classId);
        statement.setString(2, trimesterId);

        ResultSet resultSet = statement.executeQuery();
        List<ExamResult> results = new ArrayList<>();
        while (resultSet.next()) {
            ExamResult result = new ExamResult();
            result.setSubjectName(resultSet.getString("subject_name"));
            result.setStudentId(resultSet.getInt("student_id"));
            result.setScore(resultSet.getDouble("score"));
            result.setMaxScore(resultSet.getDouble("max_score"));
            results.add(result);
        }
        return results;
    }
    
    public List<ExamResult> getStudentResultsByTrimester(int studentId, String trimesterId) {
        List<ExamResult> results = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_RESULTS_BY_TRIMESTER)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, trimesterId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ExamResult result = new ExamResult();
                result.setSubjectName(rs.getString("subject_name"));
                result.setStudentId(rs.getInt("student_id"));
                result.setScore(rs.getDouble("score"));
                result.setMaxScore(rs.getDouble("max_score"));
                result.setFirstName(rs.getString("first_name"));
                result.setMiddleName(rs.getString("middle_name"));
                result.setLastName(rs.getString("last_name"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
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
