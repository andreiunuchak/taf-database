package jdbc.utils;

import jdbc.dao.hospital.Hospital;
import jdbc.dao.northwind.Northwind;
import jdbc.databases.DatabaseConnection;
import org.h2.tools.RunScript;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {

    public static void h2CreateHospitalData() {
        createTable(Hospital.DB_SCHEME, "src/test/resources/hospital_h2_db.sql");
    }

    public static void h2CreateNorthwindData() {
        createTable(Northwind.DB_SCHEME, "src/test/resources/northwind_h2_db.sql");
    }

    public static void mysqlCreateHospitalData() {
        createTable(Hospital.DB_SCHEME, "src/test/resources/hospital_mysql_db.sql");
    }

    public static void mysqlCreateNorthwindData() {
        createTable(Northwind.DB_SCHEME, "src/test/resources/northwind_mysql_db.sql");
    }

    private static void createTable(String DB_SCHEME, String resource) {
        try {
            Connection connection = DatabaseConnection.getConnection(DB_SCHEME);
            RunScript.execute(connection, new InputStreamReader(new FileInputStream(resource), StandardCharsets.UTF_8));
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
