package Tarea2.Logica.Clases;

import java.time.LocalDate;

public class Usuario {
    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private LocalDate fechaNacimiento;
    private String contrasenia;
    private String imagen;

    public Usuario() {
    }

    public Usuario( String nombre, String apellido,String direccion, String correo, LocalDate fechaNacimiento, String imagen) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nickname=" + nickname + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + ", fechaNacimiento=" + fechaNacimiento + ", contrasenia=" + contrasenia + ", imagen=" + imagen + '}';
    }
}
