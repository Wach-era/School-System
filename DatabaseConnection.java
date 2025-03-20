package pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "schoolsystem";
        String dbUsername = "root"; // Replace with your MySQL username
        String dbPassword = "Bhillary@2021"; // Replace with your MySQL password

        Class.forName(dbDriver); // Explicitly load the MySQL driver
        return DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);
    }
}
