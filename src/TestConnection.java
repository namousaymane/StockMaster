import database.DatabaseConn;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConn.getConnection();

        if (conn != null) {
            System.out.println("Connected successfully!");
        } else {
            System.out.println("Failed to connect.");
        }
    }
}
