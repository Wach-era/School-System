package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentAttendanceDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public StudentAttendanceDAO() {
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
    public void insertStudentAttendance(int studentId, Date date, String status) throws SQLException {
        String sql = "INSERT INTO StudentAttendance (student_id, date, status) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            statement.setString(3, status);
            statement.executeUpdate();
        }
    }

    // Method to retrieve attendance records for a student
    public List<StudentAttendance> getStudentAttendanceRecords(int studentId) throws SQLException {
        List<StudentAttendance> attendanceList = new ArrayList<>();
        String sql = "SELECT * FROM StudentAttendance WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int attendanceId = resultSet.getInt("attendance_id");
                Date date = resultSet.getDate("date");
                String status = resultSet.getString("status");

                StudentAttendance attendance = new StudentAttendance(attendanceId, studentId, date, status);
                attendanceList.add(attendance);
            }
        }
        return attendanceList;
    }
    
    public StudentAttendance getStudentAttendanceById(int attendanceId) {
        StudentAttendance attendance = null;
        String sql = "SELECT * FROM StudentAttendance WHERE attendance_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendanceId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                attendance = new StudentAttendance(attendanceId, studentId, date, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendance;
    }

    public boolean updateStudentAttendance(StudentAttendance attendance) {
        String sql = "UPDATE StudentAttendance SET student_id = ?, date = ?, status = ? WHERE attendance_id = ?";
        boolean rowUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendance.getStudentId());
            preparedStatement.setDate(2, (attendance.getDate()));
            preparedStatement.setString(3, attendance.getStatus());
            preparedStatement.setInt(4, attendance.getAttendanceId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteStudentAttendance(int attendanceId) {
        String sql = "DELETE FROM StudentAttendance WHERE attendance_id = ?";
        boolean rowDeleted = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, attendanceId);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public List<StudentAttendance> getAttendanceByStudentIdAndDateRange(int studentId, String startDate, String endDate) {
        List<StudentAttendance> attendanceList = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StudentAttendance WHERE student_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentAttendance attendance = new StudentAttendance(
                    resultSet.getInt("attendance_id"),
                    resultSet.getInt("student_id"),
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
    
    public Map<Date, Map<String, Integer>> getDailyAttendance(int studentId, String startDate, String endDate) 
            throws SQLException {
        String sql = "SELECT date, status, COUNT(*) as count " +
                    "FROM StudentAttendance " +
                    "WHERE student_id = ? " +
                    (startDate != null ? "AND date >= ? " : "") +
                    (endDate != null ? "AND date <= ? " : "") +
                    "GROUP BY date, status";
        
        Map<Date, Map<String, Integer>> dailyData = new java.util.TreeMap<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            stmt.setInt(paramIndex++, studentId);
            if (startDate != null) stmt.setString(paramIndex++, startDate);
            if (endDate != null) stmt.setString(paramIndex++, endDate);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("date");
                String status = rs.getString("status");
                int count = rs.getInt("count");
                
                dailyData.computeIfAbsent(date, k -> new HashMap<>())
                         .put(status, count);
            }
        }
        return dailyData;
    }
}
