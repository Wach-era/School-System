package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherSubjectDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private final String ASSIGN_SUBJECTS_TO_TEACHER_SQL = "INSERT INTO TeachersSubjects (national_id, subject_name) VALUES (?, ?)";
    private final String DELETE_ALL_SUBJECTS_FOR_TEACHER_SQL = "DELETE FROM TeachersSubjects WHERE national_id = ?";
    private static final String SELECT_SUBJECTS_BY_TEACHER = "SELECT * FROM TeachersSubjects WHERE national_id = ?";
    private final String GET_TEACHERS_BY_SUBJECT_NAME_SQL = "SELECT t.national_id, t.first_name, t.middle_name, t.last_name FROM TeachersSubjects ts JOIN teachers t ON ts.national_id = t.national_id WHERE ts.subject_name = ?";


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

    public void assignSubjectsToTeacher(int nationalId, List<String> subjectList) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN_SUBJECTS_TO_TEACHER_SQL)) {
            for (String subject : subjectList) {
                preparedStatement.setInt(1, nationalId);
                preparedStatement.setString(2, subject);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new SQLException("Error assigning subjects to teacher", e);
        }
    }

    public void deleteAllSubjectsForTeacher(String nationalId) throws SQLException {
        try (Connection connection =getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_SUBJECTS_FOR_TEACHER_SQL)) {
            preparedStatement.setString(1, nationalId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting all subjects for teacher", e);
        }
    }

    public List<String> selectSubjectsByTeacher(int nationalId) {
        List<String> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SUBJECTS_BY_TEACHER)) {
            preparedStatement.setInt(1, nationalId);
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
    
    private final String GET_SUBJECTS_BY_TEACHER_ID_SQL = "SELECT subject_name FROM TeachersSubjects WHERE national_id = ?";
    private final String DELETE_SUBJECT_FOR_TEACHER_SQL = "DELETE FROM TeachersSubjects WHERE national_id = ? AND subject_name = ?";

    public List<Subject> getSubjectsByTeacherId(String nationalId) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SUBJECTS_BY_TEACHER_ID_SQL)) {
            preparedStatement.setString(1, nationalId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String subjectName = rs.getString("subject_name");
                Subject subject = new Subject(subjectName);
                subject.setSubjectName(subjectName);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting subjects by teacher ID", e);
        }
        return subjects;
    }

    public void deleteSubjectForTeacher(String nationalId, String subjectName) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SUBJECT_FOR_TEACHER_SQL)) {
            preparedStatement.setString(1, nationalId);
            preparedStatement.setString(2, subjectName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting subject for teacher", e);
        }
    }
    
    public List<Teacher> getTeachersBySubjectName(String subjectName) throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TEACHERS_BY_SUBJECT_NAME_SQL)) {
            preparedStatement.setString(1, subjectName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setnationalId(rs.getInt("national_id"));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setMiddleName(rs.getString("middle_name"));
                teacher.setLastName(rs.getString("last_name"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new SQLException("Error getting teachers by subject name", e);
        }
        return teachers;
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
