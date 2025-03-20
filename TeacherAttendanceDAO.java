package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class TeacherAttendanceDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public TeacherAttendanceDAO() {
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
    public void insertTeacherAttendance(int nationalId, Date date, String status) throws SQLException {
        String sql = "INSERT INTO TeacherAttendance (national_id, date, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nationalId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            statement.setString(3, status);
            statement.executeUpdate();
        }
    }

    // Method to retrieve attendance records for a student
    public List<TeacherAttendance> getTeacherAttendanceRecords(int nationalId) throws SQLException {
        List<TeacherAttendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM TeacherAttendance WHERE national_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, nationalId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int attendanceId = resultSet.getInt("attendance_id");
                Date date = resultSet.getDate("date");
                String status = resultSet.getString("status");

               TeacherAttendance attendance = new TeacherAttendance(attendanceId, nationalId, date, status);
                attendanceList.add(attendance);
            }
        }
        return attendanceList;
    }
    
    public TeacherAttendance getTeacherAttendanceById(int attendanceId) {
    	TeacherAttendance attendance = null;
        String sql = "SELECT * FROM TeacherAttendance WHERE attendance_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendanceId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int nationalId = rs.getInt("national_id");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                attendance = new TeacherAttendance(attendanceId, nationalId, date, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendance;
    }

    public boolean updateTeacherAttendance(TeacherAttendance attendance) {
        String sql = "UPDATE TeacherAttendance SET national_id = ?, date = ?, status = ? WHERE attendance_id = ?";
        boolean rowUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendance.getNationalId());
            preparedStatement.setDate(2, (attendance.getDate()));
            preparedStatement.setString(3, attendance.getStatus());
            preparedStatement.setInt(4, attendance.getAttendanceId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteTeacherAttendance(int attendanceId) {
        String sql = "DELETE FROM TeacherAttendance WHERE attendance_id = ?";
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendanceId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
    
    public List<TeacherAttendance> getAttendanceByTeacherIdAndDateRange(int nationalId, String startDate, String endDate) {
        List<TeacherAttendance> attendanceList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM TeacherAttendance WHERE national_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nationalId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
            	TeacherAttendance attendance = new TeacherAttendance(
                    resultSet.getInt("attendance_id"),
                    resultSet.getInt("national_id"),
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
