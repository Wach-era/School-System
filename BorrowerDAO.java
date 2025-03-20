package pages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    private static final String INSERT_BORROWER_SQL = "INSERT INTO Borrowers (borrower_type, borrower_ref_id) VALUES (?, ?);";
    private static final String SELECT_BORROWER_BY_REF_ID = "SELECT borrower_id, borrower_type, borrower_ref_id FROM Borrowers WHERE borrower_ref_id = ?;";
    private static final String SELECT_ALL_BORROWERS = "SELECT * FROM Borrowers;";
    private static final String DELETE_BORROWER_SQL = "DELETE FROM Borrowers WHERE borrower_id = ?;";
    private static final String UPDATE_BORROWER_SQL = "UPDATE Borrowers SET borrower_type = ?, borrower_ref_id = ? WHERE borrower_id = ?;";

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

    public void insertBorrower(Borrower borrower) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BORROWER_SQL)) {
            preparedStatement.setString(1, borrower.getBorrowerType());
            preparedStatement.setInt(2, borrower.getBorrowerRefId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


    public Borrower selectBorrowerByRefId(int refId) {
        Borrower borrower = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BORROWER_BY_REF_ID)) {
            preparedStatement.setInt(1, refId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int borrowerId = rs.getInt("borrower_id");
                String borrowerType = rs.getString("borrower_type");
                borrower = new Borrower(borrowerId, borrowerType, refId);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrower;
    }

    public List<Borrower> selectAllBorrowers() {
        List<Borrower> borrower = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BORROWERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("borrower_id");
                String borrowerType = rs.getString("borrower_type");
                int borrowerRefId = rs.getInt("borrower_ref_id");
                borrower.add(new Borrower(id, borrowerType, borrowerRefId));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return borrower;
    }


    public boolean deleteBorrower(int refId) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BORROWER_SQL)) {
            statement.setInt(1, refId);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }


    public boolean updateBorrower(Borrower borrower) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BORROWER_SQL)) {
            statement.setString(1, borrower.getBorrowerType());
            statement.setInt(2, borrower.getBorrowerRefId());

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
