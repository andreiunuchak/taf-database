package jdbc.dao;

import jdbc.databases.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {
    protected Connection connection;

    protected AbstractDAO(String DB_SCHEME) {
        try {
            this.connection = DatabaseConnection.getConnection(DB_SCHEME);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
