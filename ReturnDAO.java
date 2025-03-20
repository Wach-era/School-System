package pages;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_RETURN_SQL = "INSERT INTO Returns (borrow_id, return_date, fine_amount) VALUES (?, ?, ?)";
    private static final String SELECT_RETURN_BY_ID = "SELECT * FROM Returns WHERE return_id = ?";
    private static final String SELECT_ALL_RETURNS = "SELECT * FROM Returns";
    private static final String DELETE_RETURN_SQL = "DELETE FROM Returns WHERE return_id = ?";
    private static final String UPDATE_RETURN_SQL = "UPDATE Returns SET borrow_id = ?, return_date = ?, fine_amount = ? WHERE return_id = ?";

    public ReturnDAO() {}

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

    public void insertReturn(Return returnObj) throws SQLException {
        try (Connection connection = getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RETURN_SQL)) {
            preparedStatement.setInt(1, returnObj.getBorrowId());
            preparedStatement.setDate(2, Date.valueOf(returnObj.getReturnDate()));
            preparedStatement.setBigDecimal(3, returnObj.getFineAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Return selectReturn(int returnId) {
        Return returnObj = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RETURN_BY_ID)) {
            preparedStatement.setInt(1, returnId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int borrowId = rs.getInt("borrow_id");
                LocalDate returnDate = rs.getDate("return_date").toLocalDate();
                BigDecimal fineAmount = rs.getBigDecimal("fine_amount");
                returnObj = new Return(returnId, borrowId, returnDate, fineAmount);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return returnObj;
    }

    public List<Return> selectAllReturns() {
        List<Return> returns = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RETURNS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int returnId = rs.getInt("return_id");
                int borrowId = rs.getInt("borrow_id");
                LocalDate returnDate = rs.getDate("return_date").toLocalDate();
                BigDecimal fineAmount = rs.getBigDecimal("fine_amount");
                returns.add(new Return(returnId, borrowId, returnDate, fineAmount));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return returns;
    }

    public boolean deleteReturn(int returnId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RETURN_SQL)) {
            statement.setInt(1, returnId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateReturn(Return returnObj) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RETURN_SQL)) {
            statement.setInt(1, returnObj.getBorrowId());
            statement.setDate(2, Date.valueOf(returnObj.getReturnDate()));
            statement.setBigDecimal(3, returnObj.getFineAmount());
            statement.setInt(4, returnObj.getReturnId());
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
