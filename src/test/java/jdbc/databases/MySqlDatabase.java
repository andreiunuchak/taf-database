package jdbc.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDatabase implements Database {
    private Connection connection;
    private final String DB_HOST;
    private final String USER;
    private final String PASS;

    public MySqlDatabase(String HOST, String USER, String PASS) {
        this.DB_HOST = String.format("jdbc:mysql://%s/", HOST);
        this.USER = USER;
        this.PASS = PASS;
    }

    public Connection startConnection(String DB_SCHEME) throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(DB_HOST + DB_SCHEME, USER, PASS);
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
