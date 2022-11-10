package Tarea2.Logica.Clases;

import java.time.LocalDate;

public class Usuario {
    private String nickname;
    private String nombre;
    private String apellido;
    private String correo;
    private LocalDate fechaNacimiento;
    private String contrasenia;

    private String direccion;
    private String imagen;

    private E_EstadoUsuario estado;
    public Usuario() {
    }

    public Usuario( String nombre, String apellido,String direccion, String correo, LocalDate fechaNacimiento, String imagen, E_EstadoUsuario estado) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.direccion= direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
        this.estado = estado;
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

    public E_EstadoUsuario getEstado() {
        return estado;
    }
    public void setEstado(E_EstadoUsuario estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nickname=" + nickname + ", nombre=" + nombre + ", apellido=" + apellido + ", correo=" + correo + ", fechaNacimiento=" + fechaNacimiento + ", contrasenia=" + contrasenia + ", imagen=" + imagen + '}';
    }
}
