package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class StaffAttendanceDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public StaffAttendanceDAO() {
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

    // Method to insert a new attendance record
    public void insertStaffAttendance(int staffId, Date date, String status) throws SQLException {
        String sql = "INSERT INTO StaffAttendance (staff_id, date, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            statement.setString(3, status);
            statement.executeUpdate();
        }
    }

    // Method to retrieve attendance records for a student
    public List<StaffAttendance> getStaffAttendanceRecords(int staffId) throws SQLException {
        List<StaffAttendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM StaffAttendance WHERE staff_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staffId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int attendanceId = resultSet.getInt("attendance_id");
                Date date = resultSet.getDate("date");
                String status = resultSet.getString("status");

                StaffAttendance attendance = new StaffAttendance(attendanceId, staffId, date, status);
                attendanceList.add(attendance);
            }
        }
        return attendanceList;
    }
    
    public StaffAttendance getStaffAttendanceById(int attendanceId) {
    	StaffAttendance attendance = null;
        String sql = "SELECT * FROM StaffAttendance WHERE attendance_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendanceId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int staffId = rs.getInt("staff_id");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                attendance = new StaffAttendance(attendanceId, staffId, date, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendance;
    }

    public boolean updateStaffAttendance(StaffAttendance attendance) {
        String sql = "UPDATE StaffAttendance SET staff_id = ?, date = ?, status = ? WHERE attendance_id = ?";
        boolean rowUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendance.getStaffId());
            preparedStatement.setDate(2, (attendance.getDate()));
            preparedStatement.setString(3, attendance.getStatus());
            preparedStatement.setInt(4, attendance.getAttendanceId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteStaffAttendance(int attendanceId) {
        String sql = "DELETE FROM StaffAttendance WHERE attendance_id = ?";
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendanceId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
    
    public List<StaffAttendance> getAttendanceByStaffIdAndDateRange(int staffId, String startDate, String endDate) {
        List<StaffAttendance> attendanceList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StaffAttendance WHERE staff_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	StaffAttendance attendance = new StaffAttendance(
                    resultSet.getInt("attendance_id"),
                    resultSet.getInt("staff_id"),
                    resultSet.getDate("date"),
                    resultSet.getString("status")
                );
                attendanceList.add(attendance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendanceList;
    }
}
