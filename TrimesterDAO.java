package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrimesterDAO {
    private Connection connection;
    private String jdbcURL = "jdbc:mysql://localhost:3306/schoolsystem";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Bhillary@2021";

    public TrimesterDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Trimester> getAllTrimesters() throws SQLException {
        String query = "SELECT * FROM Trimesters";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<Trimester> trimesters = new ArrayList<>();
        while (resultSet.next()) {
            Trimester trimester = new Trimester();
            trimester.setTrimesterId(resultSet.getString("trimester_id"));
            trimester.setStartDate(resultSet.getDate("start_date"));
            trimester.setEndDate(resultSet.getDate("end_date"));
            trimesters.add(trimester);
        }
        return trimesters;
    }

    public void updateTrimester(Trimester trimester) throws SQLException {
        String query = "UPDATE Trimesters SET start_date = ?, end_date = ? WHERE trimester_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, trimester.getStartDate());
        statement.setDate(2, trimester.getEndDate());
        statement.setString(3, trimester.getTrimesterId());
        statement.executeUpdate();
    }
    
    public void addTrimester(Trimester trimester) throws SQLException {
        String query = "INSERT INTO Trimesters (trimester_id, start_date, end_date) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, trimester.getTrimesterId());
        statement.setDate(2, trimester.getStartDate());
        statement.setDate(3, trimester.getEndDate());
        statement.executeUpdate();
    }
    
    public Trimester getTrimesterById(String trimesterId) throws SQLException {
        String query = "SELECT * FROM Trimesters WHERE trimester_id = ?";
        Trimester trimester = null;
        
        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, trimesterId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                trimester = new Trimester();
                trimester.setTrimesterId(resultSet.getString("trimester_id"));
                trimester.setStartDate(resultSet.getDate("start_date"));
                trimester.setEndDate(resultSet.getDate("end_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        
        return trimester;
    }


}
