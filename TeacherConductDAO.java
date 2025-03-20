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

public class TeacherConductDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public TeacherConductDAO() {
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
    public void insertTeacherConduct(TeacherConduct conduct) throws SQLException {
        String sql = "INSERT INTO TeacherConduct (national_id, date, conduct_description, action_taken) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, conduct.getNationalId());
            statement.setDate(2, new java.sql.Date(conduct.getDate().getTime()));
            statement.setString(3, conduct.getConductDescription());
            statement.setString(4, conduct.getActionTaken());
            statement.executeUpdate();
        }
    }

    public List<TeacherConduct> getAllTeacherConduct() {
        List<TeacherConduct> conductList = new ArrayList<>();
        String sql = "SELECT * FROM TeacherConduct";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int conductId = rs.getInt("conduct_id");
                int nationalId = rs.getInt("national_id");
                Date date = rs.getDate("date");
                String conductDescription = rs.getString("conduct_description");
                String actionTaken = rs.getString("action_taken");
               TeacherConduct conduct = new TeacherConduct(conductId, nationalId, date, conductDescription, actionTaken);
                conductList.add(conduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conductList;
    }

    // Method to retrieve conduct records for a student
    public List<TeacherConduct> getTeacherConductRecords(int nationalId) throws SQLException {
        List<TeacherConduct> conductList = new ArrayList<>();
        String sql = "SELECT * FROM TeacherConduct WHERE national_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nationalId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int conductId = resultSet.getInt("conduct_id");
                Date date = resultSet.getDate("date");
                String conductDescription = resultSet.getString("conduct_description");
                String actionTaken = resultSet.getString("action_taken");

                TeacherConduct conduct = new TeacherConduct(conductId, nationalId, date, conductDescription, actionTaken);
                conductList.add(conduct);
            }
        }
        return conductList;
    }
    
    public TeacherConduct getTeacherConductById(int conductId) {
    	TeacherConduct conduct = null;
        String sql = "SELECT * FROM TeacherConduct WHERE conduct_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conductId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int nationalId = rs.getInt("national_id");
                Date date = rs.getDate("date");
                String conductDescription = rs.getString("conduct_description");
                String actionTaken = rs.getString("action_taken");
                conduct = new TeacherConduct(conductId, nationalId, date, conductDescription, actionTaken);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conduct;
    }
 

    public boolean updateTeacherConduct(TeacherConduct conduct) {
        String sql = "UPDATE TeacherConduct SET national_id = ?, date = ?, conduct_description = ?, action_taken = ? WHERE conduct_id = ?";
        boolean rowUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conduct.getNationalId());
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

    public boolean deleteTeacherConduct(int conductId) {
        String sql = "DELETE FROM TeacherConduct WHERE conduct_id = ?";
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conductId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
    
    public List<TeacherConduct> getConductByTeacherIdAndDateRange(int nationalId, String startDate, String endDate) {
        List<TeacherConduct> conductList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM TeacherConduct WHERE national_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nationalId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	TeacherConduct conduct = new TeacherConduct(
                    resultSet.getInt("conduct_id"),
                    resultSet.getInt("national_id"),
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
