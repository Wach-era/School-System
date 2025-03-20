package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentClubDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_STUDENT_CLUB_SQL = "INSERT INTO StudentClubs (student_id, club_name, join_date) VALUES (?, ?, ?);";
    private static final String SELECT_STUDENT_CLUB_BY_ID = "SELECT * FROM StudentClubs WHERE student_club_id = ?;";
    private static final String SELECT_ALL_STUDENT_CLUBS = "SELECT * FROM StudentClubs;";
    private static final String DELETE_STUDENT_CLUB_SQL = "DELETE FROM StudentClubs WHERE student_club_id = ?;";
    private static final String UPDATE_STUDENT_CLUB_SQL = "UPDATE StudentClubs SET student_id = ?, club_name = ?, join_date = ? WHERE student_club_id = ?;";
    private static final String CHECK_STUDENT_EXISTS_SQL = "SELECT COUNT(*) FROM Students WHERE student_id = ?;";
    private static final String CHECK_CLUB_EXISTS_SQL = "SELECT COUNT(*) FROM Clubs WHERE club_name = ?;";
    private static final String REMOVE_MEMBER_SQL = "DELETE FROM StudentClubs WHERE student_id = ? AND club_name = ?";


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

    public void insertStudentClub(StudentClub studentClub) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_CLUB_SQL)) {
            preparedStatement.setInt(1, studentClub.getStudentId());
            preparedStatement.setString(2, studentClub.getClubName());
            preparedStatement.setDate(3, Date.valueOf(studentClub.getJoinDate()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public StudentClub selectStudentClub(int studentClubId) {
        StudentClub studentClub = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_CLUB_BY_ID)) {
            preparedStatement.setInt(1, studentClubId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                studentClub = new StudentClub(rs.getInt("student_club_id"), rs.getInt("student_id"), rs.getString("club_name"), rs.getDate("join_date").toLocalDate());
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return studentClub;
    }

    public List<StudentClub> selectAllStudentClubs() {
        List<StudentClub> studentClubs = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENT_CLUBS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                studentClubs.add(new StudentClub(rs.getInt("student_club_id"), rs.getInt("student_id"), rs.getString("club_name"), rs.getDate("join_date").toLocalDate()));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return studentClubs;
    }

    public boolean deleteStudentClub(int studentClubId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_CLUB_SQL)) {
            statement.setInt(1, studentClubId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateStudentClub(StudentClub studentClub) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_CLUB_SQL)) {
            statement.setInt(1, studentClub.getStudentId());
            statement.setString(2, studentClub.getClubName());
            statement.setDate(3, Date.valueOf(studentClub.getJoinDate()));
            statement.setInt(4, studentClub.getStudentClubId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean checkStudentExists(int studentId) {
        boolean exists = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_STUDENT_EXISTS_SQL)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exists;
    }

    public boolean checkClubExists(String clubName) {
        boolean exists = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_CLUB_EXISTS_SQL)) {
            preparedStatement.setString(1, clubName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return exists;
    }
    
    public void removeMember(int studentId, String clubName) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_MEMBER_SQL)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, clubName);
            preparedStatement.executeUpdate();
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

    public List<StudentClub> getClubsByStudentIdAndDateRange(int studentId, String startDate, String endDate) {
        List<StudentClub> clubs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM StudentClubs WHERE student_id = ? AND join_date BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentClub club = new StudentClub(
                    resultSet.getInt("student_club_id"),
                    resultSet.getInt("student_id"),
                    resultSet.getString("club_name"),
                    resultSet.getDate("join_date").toLocalDate()
                );
                clubs.add(club);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clubs;
    }
}
