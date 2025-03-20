package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/schoolsystem";
        String username = "root";
        String password = "Bhillary@2021";
        
        try {
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
