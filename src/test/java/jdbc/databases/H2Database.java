package jdbc.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Database implements Database {
    private static Connection connection;
    private static final String DB_HOST = "jdbc:h2:mem:";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";

    public Connection startConnection(String DB_SCHEME) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_HOST + DB_SCHEME, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
