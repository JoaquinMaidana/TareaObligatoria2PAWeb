package Tarea2.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    public static Connection getConnection() {
        Connection connection = null;
        String url = System.getProperty("DB_URL");
        System.out.println(url);
        String user = System.getProperty("DB_USER");
        System.out.println(user);
        String password = System.getProperty("DB_PASS");
        System.out.println(password);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("la cone es:"+connection);
            if (connection != null)
                System.out.println("Conexion a la base de datos establecida");

        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
