package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolClassDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_CLASS_SQL = "INSERT INTO Classes (class_id, class_teacher, max_students, room_number) VALUES (?, ?, ?, ?);";
    private static final String SELECT_CLASS_BY_ID = "SELECT * FROM Classes WHERE class_id = ?;";
    private static final String SELECT_ALL_CLASSES = 
        "SELECT c.class_id, c.class_teacher, c.max_students, c.room_number, " +
        "CONCAT(t.first_name, ' ', t.middle_name, ' ', t.last_name) AS teacher_name " +
        "FROM Classes c " +
        "JOIN Teachers t ON c.class_teacher = t.national_id;";
    private static final String DELETE_CLASS_SQL = "DELETE FROM Classes WHERE class_id = ?;";
    private static final String UPDATE_CLASS_SQL = "UPDATE Classes SET class_teacher = ?, max_students = ?, room_number = ? WHERE class_id = ?;";
    private static final String CHECK_TEACHER_EXISTS_SQL = "SELECT 1 FROM Teachers WHERE national_id = ?;";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertClass(SchoolClass schoolClass) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLASS_SQL)) {
            preparedStatement.setString(1, schoolClass.getClassId());
            preparedStatement.setInt(2, schoolClass.getClassTeacher());
            preparedStatement.setInt(3, schoolClass.getMaxStudents());
            preparedStatement.setString(4, schoolClass.getRoomNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public SchoolClass selectClassById(String classId) {
        SchoolClass schoolClass = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASS_BY_ID)) {
            preparedStatement.setString(1, classId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                schoolClass = new SchoolClass(
                    rs.getString("class_id"),
                    rs.getInt("class_teacher"),
                    rs.getInt("max_students"),
                    rs.getString("room_number")
                );
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return schoolClass;
    }

    public List<SchoolClass> selectAllClasses() {
        List<SchoolClass> classes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLASSES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                classes.add(new SchoolClass(
                    rs.getString("class_id"),
                    rs.getInt("class_teacher"),
                    rs.getInt("max_students"),
                    rs.getString("room_number"),
                    rs.getString("teacher_name")
                ));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return classes;
    }

    public boolean deleteClass(String classId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLASS_SQL)) {
            statement.setString(1, classId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateClass(SchoolClass schoolClass) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLASS_SQL)) {
            statement.setInt(1, schoolClass.getClassTeacher());
            statement.setInt(2, schoolClass.getMaxStudents());
            statement.setString(3, schoolClass.getRoomNumber());
            statement.setString(4, schoolClass.getClassId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    public boolean checkTeacherExists(int teacherId) {
        boolean exists = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_TEACHER_EXISTS_SQL)) {
            preparedStatement.setInt(1, teacherId);
            ResultSet rs = preparedStatement.executeQuery();

            exists = rs.next();
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exists;
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
