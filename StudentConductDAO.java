package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class StudentConductDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public StudentConductDAO() {
        this.connection = getConnection();
    }

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
    public void insertStudentConduct(StudentConduct conduct) throws SQLException {
        String sql = "INSERT INTO StudentConduct (student_id, date, conduct_description, action_taken) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, conduct.getStudentId());
            statement.setDate(2, new java.sql.Date(conduct.getDate().getTime()));
            statement.setString(3, conduct.getConductDescription());
            statement.setString(4, conduct.getActionTaken());
            statement.executeUpdate();
        }
    }

    public List<StudentConduct> getAllStudentConduct() {
        List<StudentConduct> conductList = new ArrayList<>();
        String sql = "SELECT * FROM StudentConduct";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int conductId = rs.getInt("conduct_id");
                int studentId = rs.getInt("student_id");
                Date date = rs.getDate("date");
                String conductDescription = rs.getString("conduct_description");
                String actionTaken = rs.getString("action_taken");
                StudentConduct conduct = new StudentConduct(conductId, studentId, date, conductDescription, actionTaken);
                conductList.add(conduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conductList;
    }

    // Method to retrieve conduct records for a student
    public List<StudentConduct> getStudentConductRecords(int studentId) throws SQLException {
        List<StudentConduct> conductList = new ArrayList<>();
        String sql = "SELECT * FROM StudentConduct WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int conductId = resultSet.getInt("conduct_id");
                Date date = resultSet.getDate("date");
                String conductDescription = resultSet.getString("conduct_description");
                String actionTaken = resultSet.getString("action_taken");

                StudentConduct conduct = new StudentConduct(conductId, studentId, date, conductDescription, actionTaken);
                conductList.add(conduct);
            }
        }
        return conductList;
    }
    
    public StudentConduct getStudentConductById(int conductId) {
        StudentConduct conduct = null;
        String sql = "SELECT * FROM StudentConduct WHERE conduct_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conductId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                Date date = rs.getDate("date");
                String conductDescription = rs.getString("conduct_description");
                String actionTaken = rs.getString("action_taken");
                conduct = new StudentConduct(conductId, studentId, date, conductDescription, actionTaken);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conduct;
    }

    public boolean updateStudentConduct(StudentConduct conduct) {
        String sql = "UPDATE StudentConduct SET student_id = ?, date = ?, conduct_description = ?, action_taken = ? WHERE conduct_id = ?";
        boolean rowUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conduct.getStudentId());
            preparedStatement.setDate(2, (conduct.getDate()));
            preparedStatement.setString(3, conduct.getConductDescription());
            preparedStatement.setString(4, conduct.getActionTaken());
            preparedStatement.setInt(5, conduct.getConductId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteStudentConduct(int conductId) {
        String sql = "DELETE FROM StudentConduct WHERE conduct_id = ?";
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conductId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public List<StudentConduct> getConductByStudentIdAndDateRange(int studentId, String startDate, String endDate) {
        List<StudentConduct> conductList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StudentConduct WHERE student_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentConduct conduct = new StudentConduct(
                    resultSet.getInt("conduct_id"),
                    resultSet.getInt("student_id"),
                    resultSet.getDate("date"),
                    resultSet.getString("conduct_description"),
                    resultSet.getString("action_taken")
                );
                conductList.add(conduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conductList;
    }
}
