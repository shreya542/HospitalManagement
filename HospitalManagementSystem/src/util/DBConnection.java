package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String url = PropertyUtil.getPropertyString("db.url");
        String user = PropertyUtil.getPropertyString("db.username");
        String password = PropertyUtil.getPropertyString("db.password");

        // Ensure the JDBC driver is registered
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Use the correct driver for MySQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.");
        }

        return DriverManager.getConnection(url, user, password);
    }
}
