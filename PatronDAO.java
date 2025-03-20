package pages;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatronDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_PATRON_SQL = "INSERT INTO Patrons (national_id, club_name) VALUES (?, ?);";
    private static final String SELECT_PATRON_BY_ID = "SELECT * FROM Patrons WHERE patron_id = ?;";
    private static final String SELECT_ALL_PATRONS = "SELECT * FROM Patrons;";
    private static final String DELETE_PATRON_SQL = "DELETE FROM Patrons WHERE patron_id = ?;";
    private static final String UPDATE_PATRON_SQL = "UPDATE Patrons SET national_id = ?, club_name = ? WHERE patron_id = ?;";
    private static final String CHECK_TEACHER_EXISTS_SQL = "SELECT COUNT(*) FROM Teachers WHERE national_id = ?;";
    private static final String CHECK_CLUB_EXISTS_SQL = "SELECT COUNT(*) FROM Clubs WHERE club_name = ?;";

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

    public void insertPatron(Patron patron) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PATRON_SQL)) {
            preparedStatement.setInt(1, patron.getNationalId());
            preparedStatement.setString(2, patron.getClubName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Patron selectPatron(int patronId) {
        Patron patron = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PATRON_BY_ID)) {
            preparedStatement.setInt(1, patronId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                patron = new Patron(rs.getInt("patron_id"), rs.getInt("national_id"), rs.getString("club_name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return patron;
    }

    public List<Patron> selectAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PATRONS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                patrons.add(new Patron(rs.getInt("patron_id"), rs.getInt("national_id"), rs.getString("club_name")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return patrons;
    }

    public boolean deletePatron(int patronId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PATRON_SQL)) {
            statement.setInt(1, patronId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updatePatron(Patron patron) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PATRON_SQL)) {
            statement.setInt(1, patron.getNationalId());
            statement.setString(2, patron.getClubName());
            statement.setInt(3, patron.getPatronId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean checkTeacherExists(int nationalId) {
        boolean exists = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_TEACHER_EXISTS_SQL)) {
            preparedStatement.setInt(1, nationalId);
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
    
    public List<Patron> getClubsByTeacherIdAndDateRange(int nationalId, String startDate, String endDate) {
        List<Patron> clubs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Patrons WHERE national_id = ? AND patron_id BETWEEN ? AND ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, nationalId);
            statement.setString(2, startDate);
            statement.setString(3, endDate);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Patron club = new Patron(rs.getInt("patron_id"), rs.getInt("national_id"), rs.getString("club_name"));

                clubs.add(club);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
