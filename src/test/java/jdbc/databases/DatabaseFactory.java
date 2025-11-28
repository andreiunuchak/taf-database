package jdbc.databases;

public class DatabaseFactory {
    public static Database getDatabase(String environment) {
        return switch (environment) {
            case "mysql" -> new MySqlDatabase(System.getProperty("host"), System.getProperty("user"), System.getProperty("password"));
            default -> new H2Database();
        };
    }
}
