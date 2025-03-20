package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AcademicYearDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_YEAR_SQL = "INSERT INTO AcademicYear (year_id, start_date, end_date) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_YEARS = "SELECT * FROM AcademicYear;";

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

    public void insertYear(AcademicYear academicYear) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_YEAR_SQL)) {
            preparedStatement.setInt(1, academicYear.getYearId());
            preparedStatement.setDate(2, academicYear.getStartDate());
            preparedStatement.setDate(3, academicYear.getEndDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public List<AcademicYear> selectAllYears() {
        List<AcademicYear> academicYears = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_YEARS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                academicYears.add(new AcademicYear(
                    rs.getInt("year_id"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date")
                ));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return academicYears;
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
