package Tarea2.Logica.Interfaces;

import java.io.File;
import java.io.FileInputStream;

public interface IDatabase {
    String guardarImagen(FileInputStream imagen);
    String guardarImagen(File imagen);
}
