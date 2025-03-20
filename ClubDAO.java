package pages;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_CLUB_SQL = "INSERT INTO Clubs (club_name) VALUES (?);";
    private static final String SELECT_CLUB_BY_NAME = "SELECT * FROM Clubs WHERE club_name = ?;";
    private static final String SELECT_ALL_CLUBS = "SELECT * FROM Clubs;";
    private static final String DELETE_CLUB_SQL = "DELETE FROM Clubs WHERE club_name = ?;";
    private static final String UPDATE_CLUB_SQL = "UPDATE Clubs SET club_name = ? WHERE club_name = ?;";
    private static final String SELECT_ALL_CLUBS_WITH_PATRON_DETAILS = 
            "SELECT c.club_name, " +
            "CONCAT(t.first_name, ' ', t.middle_name, ' ', t.last_name) AS patron_name, " +
            "t.national_id AS patron_national_id " +
            "FROM Clubs c " +
            "LEFT JOIN Patrons p ON c.club_name = p.club_name " +
            "LEFT JOIN Teachers t ON p.national_id = t.national_id";

    
  
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

    public void insertClub(Club club) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLUB_SQL)) {
            preparedStatement.setString(1, club.getClubName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Club selectClubByName(String clubName) {
        Club club = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLUB_BY_NAME)) {
            preparedStatement.setString(1, clubName);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                club = new Club(rs.getString("club_name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return club;
    }

    public List<Club> selectAllClubs() {
        List<Club> clubs = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLUBS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                clubs.add(new Club(rs.getString("club_name")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return clubs;
    }

    public boolean deleteClub(String clubName) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLUB_SQL)) {
            statement.setString(1, clubName);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateClub(Club club, String oldClubName) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLUB_SQL)) {
            statement.setString(1, club.getClubName());
            statement.setString(2, oldClubName);
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    public List<Club> selectAllClubsWithPatronDetails() {
        List<Club> clubs = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLUBS_WITH_PATRON_DETAILS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String clubName = rs.getString("club_name");
                String patronName = rs.getString("patron_name");
                String patronNationalId = rs.getString("patron_national_id");
                clubs.add(new Club(clubName, patronName, patronNationalId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }
    
    public List<StudentClub> selectMembersByClubName(String clubName) {
        List<StudentClub> members = new ArrayList<>();
        String SELECT_MEMBERS_BY_CLUB_NAME = 
            "SELECT sc.student_club_id, sc.student_id, s.first_name, s.middle_name, s.last_name, sc.club_name, sc.join_date " +
            "FROM StudentClubs sc " +
            "JOIN Students s ON sc.student_id = s.student_id " +
            "WHERE sc.club_name = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MEMBERS_BY_CLUB_NAME)) {
            preparedStatement.setString(1, clubName);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int studentClubId = rs.getInt("student_club_id");
                int studentId = rs.getInt("student_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String clubNameDb = rs.getString("club_name");
                LocalDate joinDate = rs.getDate("join_date").toLocalDate();

                members.add(new StudentClub(studentClubId, studentId, firstName, middleName, lastName, clubNameDb, joinDate));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return members;
    }
    
    public List<StudentClub> selectClubsByStudentId(int studentId) throws SQLException {
        List<StudentClub> clubs = new ArrayList<>();
        String sql = "SELECT sc.student_club_id, sc.student_id, s.first_name, s.middle_name, s.last_name, sc.club_name, sc.join_date " +
                     "FROM StudentClubs sc " +
                     "JOIN Students s ON sc.student_id = s.student_id " +
                     "WHERE sc.student_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, studentId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int studentClubId = rs.getInt("student_club_id");
                String firstName = rs.getString("first_name");
                String middleName = rs.getString("middle_name");
                String lastName = rs.getString("last_name");
                String clubName = rs.getString("club_name");
                LocalDate joinDate = rs.getDate("join_date").toLocalDate();
                clubs.add(new StudentClub(studentClubId, studentId, firstName, middleName, lastName, clubName, joinDate));
            }
        }
        return clubs;
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
