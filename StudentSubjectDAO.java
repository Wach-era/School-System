package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentSubjectDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

   // private static final String ASSIGN_SUBJECT_TO_STUDENT = "INSERT INTO StudentSubjects (student_id, subject_name) VALUES (?, ?)";
    private static final String SELECT_SUBJECTS_BY_STUDENT = "SELECT * FROM StudentSubjects WHERE student_id = ?";

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

    public void assignSubjectsToStudent(int studentId, List<String> subjects) throws SQLException {
        String sql =  "INSERT INTO StudentSubjects (student_id, subject_name) VALUES (?, ?)";
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (String subject : subjects) {
            statement.setInt(1, studentId);
            statement.setString(2, subject);
            statement.addBatch();
        }
        statement.executeBatch();
    }

    public List<String> selectSubjectsByStudent(int studentId) {
        List<String> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SUBJECTS_BY_STUDENT)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                subjects.add(subjectName);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return subjects;
    }
    
    public List<Student> getStudentsBySubjectCode(String subjectCode) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.student_id, s.first_name, s.middle_name, s.last_name " +
                     "FROM Students s " +
                     "JOIN StudentSubjects ss ON s.student_id = ss.student_id " +
                     "WHERE ss.subject_name = ?";

        try (Connection connection = getConnection();
        		PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subjectCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " +
                                  (resultSet.getString("middle_name") != null ? resultSet.getString("middle_name") + " " : "") +
                                  resultSet.getString("last_name");
                Student student = new Student(
                    resultSet.getInt("student_id"),
                    fullName
                );
                students.add(student);
            }
        }

        return students;
    }
    
    public List<Subject> getSubjectsByStudentId(int studentId) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.subject_name, s.subject_name, s.section " +
                     "FROM Subjects s " +
                     "JOIN StudentSubjects ss ON s.subject_name = ss.subject_name " +
                     "WHERE ss.student_id = ?";

        try (Connection connection = getConnection();
        		PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject(
                    resultSet.getString("subject_name"),
                    resultSet.getString("section")
                );
                subjects.add(subject);
            }
        }

        return subjects;
    }
    
    private static final String DELETE_ALL_SUBJECTS_FOR_STUDENT_SQL = "DELETE FROM StudentSubjects WHERE student_id = ?";

    public boolean deleteAllSubjectsForStudent(int studentId) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_SUBJECTS_FOR_STUDENT_SQL)) {
            preparedStatement.setInt(1, studentId);
            return preparedStatement.executeUpdate() > 0;
        }
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
