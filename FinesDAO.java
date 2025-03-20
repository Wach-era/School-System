package pages;

import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinesDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_FINE_SQL = "INSERT INTO Fines (borrow_id, fine_amount, fine_date, paid) VALUES (?, ?, ?, ?)";
    private static final String SELECT_FINE_BY_ID = "SELECT * FROM Fines WHERE fine_id = ?";
    private static final String SELECT_ALL_FINES = "SELECT * FROM Fines";
    private static final String DELETE_FINE_SQL = "DELETE FROM Fines WHERE fine_id = ?";
    private static final String UPDATE_FINE_SQL = "UPDATE Fines SET borrow_id = ?, fine_amount = ?, fine_date = ?, paid = ? WHERE fine_id = ?";
    private static final String UPDATE_FINE_PAYMENT_SQL = "UPDATE Fines SET paid = TRUE WHERE fine_id = ?";

    public FinesDAO() {}

    protected Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertFine(Fine fine) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FINE_SQL)) {
            preparedStatement.setInt(1, fine.getBorrowId());
            preparedStatement.setBigDecimal(2, fine.getFineAmount());
            preparedStatement.setDate(3, Date.valueOf(fine.getFineDate()));
            preparedStatement.setBoolean(4, fine.isPaid());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Fine selectFine(int fineId) {
        Fine fine = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FINE_BY_ID)) {
            preparedStatement.setInt(1, fineId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int borrowId = resultSet.getInt("borrow_id");
                BigDecimal fineAmount = resultSet.getBigDecimal("fine_amount");
                LocalDate fineDate = resultSet.getDate("fine_date").toLocalDate();
                boolean paid = resultSet.getBoolean("paid");

                fine = new Fine(fineId, borrowId, fineAmount, fineDate, paid);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return fine;
    }

    public List<Fine> selectAllFines() {
        List<Fine> fines = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FINES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int fineId = resultSet.getInt("fine_id");
                int borrowId = resultSet.getInt("borrow_id");
                BigDecimal fineAmount = resultSet.getBigDecimal("fine_amount");
                LocalDate fineDate = resultSet.getDate("fine_date").toLocalDate();
                boolean paid = resultSet.getBoolean("paid");

                fines.add(new Fine(fineId, borrowId, fineAmount, fineDate, paid));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return fines;
    }

    public boolean deleteFine(int fineId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FINE_SQL)) {
            statement.setInt(1, fineId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateFine(Fine fine) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FINE_SQL)) {
            statement.setInt(1, fine.getBorrowId());
            statement.setBigDecimal(2, fine.getFineAmount());
            statement.setDate(3, Date.valueOf(fine.getFineDate()));
            statement.setBoolean(4, fine.isPaid());
            statement.setInt(5, fine.getFineId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean payFine(int fineId) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FINE_PAYMENT_SQL)) {
            statement.setInt(1, fineId);

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
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
