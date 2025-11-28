package jdbc.utils;

public class SystemUtils {
    public static String getSystemPropertyDatabase() {
        return System.getProperty("database", "h2");
    }
}
