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

public class StaffConductDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public StaffConductDAO() {
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
    public void insertStaffConduct(StaffConduct conduct) throws SQLException {
        String sql = "INSERT INTO StaffConduct (Staff_id, date, conduct_description, action_taken) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, conduct.getStaffId());
            statement.setDate(2, new java.sql.Date(conduct.getDate().getTime()));
            statement.setString(3, conduct.getConductDescription());
            statement.setString(4, conduct.getActionTaken());
            statement.executeUpdate();
        }
    }

    public List<StaffConduct> getAllStaffConduct() {
        List<StaffConduct> conductList = new ArrayList<>();
        String sql = "SELECT * FROM StaffConduct";
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int conductId = rs.getInt("conduct_id");
                int staffId = rs.getInt("staff_id");
                Date date = rs.getDate("date");
                String conductDescription = rs.getString("conduct_description");
                String actionTaken = rs.getString("action_taken");
                StaffConduct conduct = new StaffConduct(conductId, staffId, date, conductDescription, actionTaken);
                conductList.add(conduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conductList;
    }

    // Method to retrieve conduct records for a student
    public List<StaffConduct> getStaffConductRecords(int staffId) throws SQLException {
        List<StaffConduct> conductList = new ArrayList<>();
        String sql = "SELECT * FROM StaffConduct WHERE Staff_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int conductId = resultSet.getInt("conduct_id");
                Date date = resultSet.getDate("date");
                String conductDescription = resultSet.getString("conduct_description");
                String actionTaken = resultSet.getString("action_taken");

                StaffConduct conduct = new StaffConduct(conductId, staffId, date, conductDescription, actionTaken);
                conductList.add(conduct);
            }
        }
        return conductList;
    }
    
    public StaffConduct getStaffConductById(int conductId) {
    	StaffConduct conduct = null;
        String sql = "SELECT * FROM StaffConduct WHERE conduct_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conductId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int staffId = rs.getInt("staff_id");
                Date date = rs.getDate("date");
                String conductDescription = rs.getString("conduct_description");
                String actionTaken = rs.getString("action_taken");
                conduct = new StaffConduct(conductId, staffId, date, conductDescription, actionTaken);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conduct;
    }
 

    public boolean updateStaffConduct(StaffConduct conduct) {
        String sql = "UPDATE StaffConduct SET staff_id = ?, date = ?, conduct_description = ?, action_taken = ? WHERE conduct_id = ?";
        boolean rowUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conduct.getStaffId());
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

    public boolean deleteStaffConduct(int conductId) {
        String sql = "DELETE FROM StaffConduct WHERE conduct_id = ?";
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, conductId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
    
    public List<StaffConduct> getConductByStaffIdAndDateRange(int staffId, String startDate, String endDate) {
        List<StaffConduct> conductList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StaffConduct WHERE staff_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	StaffConduct conduct = new StaffConduct(
                    resultSet.getInt("conduct_id"),
                    resultSet.getInt("staff_id"),
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
