package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    // Oculta tus datos con variables de entorno
    private static final String HOST = System.getenv().getOrDefault("DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("DB_PORT", "aqui el puerto 3306");
    private static final String DB = System.getenv().getOrDefault("DB_DB", "aqui la base de datos");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "aqui el user");
    private static final String PASS = System.getenv().getOrDefault("DB_PASS", "aqui el pass");

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        Properties p = new Properties();
        p.setProperty("user", USER);
        p.setProperty("password", PASS);
        p.setProperty("requireSSL", "true");
        p.setProperty("useSSL", "true");

        // Fuerza carga del driver (por seguridad)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver MySQL", e);
        }

        System.out.println("🔗 URL: " + URL); // Solo para debug

        return DriverManager.getConnection(URL, p);
    }
}