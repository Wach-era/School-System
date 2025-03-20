package pages;
import java.sql.*;
import java.util.*;

public class FeeStructureDAO {
	 private Connection connection;
	    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
	    private String jdbcUsername = "root";
	    private String jdbcPassword = "Bhillary@2021";
	    
	    
	public FeeStructureDAO() {
        this.connection = getConnection();
    }
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<FeeStructure> getAllFees() throws SQLException {
        List<FeeStructure> fees = new ArrayList<>();
        String query = "SELECT * FROM FeesStructure";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            FeeStructure fee = new FeeStructure(
                resultSet.getInt("fee_id"),
                resultSet.getString("trimester_id"),
                resultSet.getString("description"),
                resultSet.getDouble("amount")
            );
            fees.add(fee);
        }

        return fees;
    }

    public void addFee(FeeStructure fee) throws SQLException {
        String query = "INSERT INTO FeesStructure (trimester_id, description, amount) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fee.getTrimesterId());
        preparedStatement.setString(2, fee.getDescription());
        preparedStatement.setDouble(3, fee.getAmount());
        preparedStatement.executeUpdate();
    }

    public void updateFee(FeeStructure fee) throws SQLException {
        String query = "UPDATE FeesStructure SET trimester_id = ?, description = ?, amount = ? WHERE fee_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fee.getTrimesterId());
        preparedStatement.setString(2, fee.getDescription());
        preparedStatement.setDouble(3, fee.getAmount());
        preparedStatement.setInt(4, fee.getFeeId());
        preparedStatement.executeUpdate();
    }
    
    public void updateFeesByTrimesterId(String trimesterId, List<FeeStructure> fees) throws SQLException {
        String deleteQuery = "DELETE FROM FeesStructure WHERE trimester_id = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setString(1, trimesterId);
            deleteStatement.executeUpdate();
        }

        String insertQuery = "INSERT INTO FeesStructure (trimester_id, description, amount) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            for (FeeStructure fee : fees) {
                insertStatement.setString(1, fee.getTrimesterId());
                insertStatement.setString(2, fee.getDescription());
                insertStatement.setDouble(3, fee.getAmount());
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        }
    }

    public void deleteFee(int feeId) throws SQLException {
        String query = "DELETE FROM FeesStructure WHERE fee_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, feeId);
        preparedStatement.executeUpdate();
    }
    
    public void deleteFeesByTrimesterId(String trimesterId) throws SQLException {
        String sql = "DELETE FROM FeeStructure WHERE trimester_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trimesterId);
            statement.executeUpdate();
        }
    }
    
    public List<FeeStructure> getFeesByTrimester(String trimesterId) throws SQLException {
        List<FeeStructure> fees = new ArrayList<>();
        String sql = "SELECT * FROM FeesStructure WHERE trimester_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, trimesterId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeeStructure fee = new FeeStructure(
                    rs.getInt("fee_id"),
                    rs.getString("trimester_id"),
                    rs.getString("description"),
                    rs.getDouble("amount")
                );
                fees.add(fee);
            }
        }
        return fees;
    }
}
