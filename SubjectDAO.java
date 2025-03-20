package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String SELECT_ALL_SUBJECTS = "SELECT * FROM Subjects";
    private static final String SELECT_SUBJECT_BY_NAME = "SELECT * FROM Subjects WHERE subject_name = ?";

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

    public List<Subject> selectAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SUBJECTS);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                String subjectCode = rs.getString("subject_code");
                String departmentId = rs.getString("department_id");
                boolean isCompulsory = rs.getBoolean("is_compulsory");
                String section = rs.getString("section");
                subjects.add(new Subject(subjectName, subjectCode, departmentId, isCompulsory, section));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return subjects;
    }

    public Subject selectSubject(String subjectName) {
        Subject subject = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SUBJECT_BY_NAME);) {
            preparedStatement.setString(1, subjectName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String subjectCode = rs.getString("subject_code");
                String departmentId = rs.getString("department_id");
                boolean isCompulsory = rs.getBoolean("is_compulsory");
                String section = rs.getString("section");
                subject = new Subject(subjectName, subjectCode, departmentId, isCompulsory, section);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return subject;
    }
    
    public List<Subject> listAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SUBJECTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                String section = rs.getString("section");
                subjects.add(new Subject(subjectName, section));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return subjects;
    }
    public List<Subject> selectSubjectsByStudentId(int studentId) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.subject_name, s.department_id, s.is_compulsory, s.section " +
                     "FROM Subjects s " +
                     "JOIN StudentSubjects ss ON s.subject_name = ss.subject_name " + 
                     "WHERE ss.student_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                String departmentId = rs.getString("department_id");
                boolean isCompulsory = rs.getBoolean("is_compulsory");
                String section = rs.getString("section");

                subjects.add(new Subject(subjectName, departmentId, isCompulsory, section)); 
            }
        }
        return subjects;
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
