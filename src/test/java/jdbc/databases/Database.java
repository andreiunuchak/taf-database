package jdbc.databases;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection startConnection(String DB_SCHEME) throws SQLException, ClassNotFoundException;

    void closeConnection() throws SQLException;
}
