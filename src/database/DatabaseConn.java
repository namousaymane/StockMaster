package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConn {

    private static final String URL = "jdbc:mysql://localhost:3306/inventory_management";
    private static final String USER = "your_username";
    private static final String PASSWORD = "you_password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            e.printStackTrace();
            return null;
        }
    }
}
