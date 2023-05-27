package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private final Properties jdbcProps;
    private Connection connection = null;

    public JdbcUtils(Properties jdbcProps) {
        this.jdbcProps = jdbcProps;
    }

    private Connection getNewConnection() {
        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");

        Connection conn = null;
        try {
            if (user != null && pass != null) {
                conn = DriverManager.getConnection(url, user, pass);
            } else {
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.out.println("DB connection error: " + e.getMessage());
        }
        return conn;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = getNewConnection();
            }
        } catch (SQLException e) {
            System.out.println("DB connection error: " + e.getMessage());
        }
        return connection;
    }
}