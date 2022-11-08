package Tarea2.Logica.Controladores;

import Tarea2.Logica.Clases.Usuario;
import Tarea2.Logica.Interfaces.IUsuario;
import Tarea2.Persistencia.ConexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsuarioController implements IUsuario {
    private static UsuarioController instance;

    private UsuarioController() {
    }

    public static UsuarioController getInstance() {
        if (instance == null) {
            instance = new UsuarioController();
        }
        return instance;
    }
    @Override
    public Map<String, Usuario> obtenerUsuarios() {
        Map<String, Usuario> usuarios = new HashMap<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String selectUsuarios = "SELECT * " +
                "FROM usuarios as U ";

        try {
            connection = ConexionDB.getConnection();

            // Obtenemos los usuarios
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectUsuarios);
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String correo = resultSet.getString("correo");
                String direccion = resultSet.getString("direccion");
                LocalDate fechaNacimiento = resultSet.getDate("fechaNac").toLocalDate();
                String imagen = resultSet.getString("imagen");

                Usuario usuario = new Usuario( nombre, apellido, direccion, correo, fechaNacimiento, imagen);
                usuarios.put(correo,usuario);
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al conectar con la base de datos", e);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al obtener los usuarios", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Error al cerrar la conexión a la base de datos", e);
            }
        }
        return usuarios;
    }
}
