package Tarea2.Logica.Fabrica;

import Tarea2.Logica.Controladores.DatabaseController;
import Tarea2.Logica.Controladores.UsuarioController;
import Tarea2.Logica.Interfaces.IDatabase;
import Tarea2.Logica.Interfaces.IUsuario;

public class Fabrica {
    private static Fabrica instance;
    private IUsuario usuario;
    private IDatabase database;

    private Fabrica() {}

    public static Fabrica getInstance() {
        if (instance == null) {
            instance = new Fabrica();
        }
        return instance;
    }

    public IUsuario getIUsuario() {
        UsuarioController usuarioController = UsuarioController.getInstance();
        return usuarioController;
    }
    public IDatabase getIDatabase() {
        DatabaseController databaseController = DatabaseController.getInstance();
        return databaseController;
    }
}
