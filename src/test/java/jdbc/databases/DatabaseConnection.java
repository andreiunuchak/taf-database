package jdbc.databases;

import jdbc.utils.SystemUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final Database database = DatabaseFactory.getDatabase(SystemUtils.getSystemPropertyDatabase());
    private static Connection connection;

    private DatabaseConnection() {
    }

    public static Connection getConnection(String DB_SCHEME) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = database.startConnection(DB_SCHEME);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            database.closeConnection();
            connection = null;
        }
    }
}
