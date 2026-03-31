import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            // FORCE load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_db",
                "root",
                "Root@1234"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}