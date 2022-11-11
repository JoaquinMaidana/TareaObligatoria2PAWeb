package Tarea2.serverlets.Usuario;

import Tarea2.Logica.Clases.E_EstadoUsuario;
import Tarea2.Logica.Clases.Usuario;
import Tarea2.Logica.Fabrica.Fabrica;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.Optional;

@WebServlet(name = "AltaUsuario", value = "/alta")
@MultipartConfig
public class Alta extends HttpServlet {

    Fabrica fabrica;

    public void init() {
        fabrica = Fabrica.getInstance();
    }

    protected void dispatchPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher view = request.getRequestDispatcher(page);
        view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        dispatchPage("/pages/alta.jsp", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String fechaNac_str = request.getParameter("fechaNac");
        String direccion = request.getParameter("direccion");
        Part part=request.getPart("imagen");

        System.out.println("El nombre es "+nombre);
        System.out.println("El apelido es "+apellido);
        System.out.println("El correo es "+correo);
        System.out.println("La fecha es "+ fechaNac_str);
        System.out.println("la direccion es "+direccion);

        // Validar los datos traidos del formulario:
        //TODO: Pensar si vale la pena verificar el tamaño de string de los campos

        //error cuando alguno de los campos son vacios
        if(camposVacios( nombre, apellido, correo, fechaNac_str,direccion)) {
            request.setAttribute("message", "Los campos obligatorios no pueden ser vacios");
            request.setAttribute("messageType", "error");
            dispatchPage("/pages/alta.jsp", request, response);
            return;
        }


        LocalDate fechaNac = LocalDate.parse(fechaNac_str); // ahora que sabemos que no es vacio, lo podemos parsear



        if(nombreExistenteCorreo(correo)){
            request.setAttribute("message", "El correo ingresado ya existe en la base de datos");
            request.setAttribute("messageType", "error");
            dispatchPage("/pages/alta.jsp", request, response);
            return;
        }
        //error para cuando el correo NO posea un formato de correo
        if(!esFormatoCorreo(correo)){
            request.setAttribute("message", "Formato de correo invalido");
            request.setAttribute("messageType", "error");
            dispatchPage("/pages/alta.jsp", request, response);
            return;
        }


        //La fecha no es valida porque no nacio mañana
        if(!fechaValida(fechaNac)){
            request.setAttribute("message", "La fecha no es valida");
            request.setAttribute("messageType", "error");
            dispatchPage("/pages/alta.jsp", request, response);
            return;
        }

        String urlImagen="https://i.imgur.com/e4W1PV0.png";
        try {
            if (part.getSize() != 0) {
                InputStream inputImagen = part.getInputStream();
                //urlImagen = Fabrica.getInstance().getIDatabase().guardarImagen((FileInputStream) inputImagen);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error al guardar la imagen");
            request.setAttribute("messageType", "error");
            dispatchPage("/pages/alta.jsp", request, response);
            return;
        }

            Usuario usuario = new Usuario( nombre, apellido,direccion, correo, fechaNac, urlImagen, E_EstadoUsuario.ACTIVADO);

        try {
            // Se crea el usuario en la base de datos
            fabrica.getIUsuario().altaUsuario(usuario);

            // Redireccionar a la pantalla de login
            request.getSession().setAttribute("message", "Usuario creado exitosamente");
            request.setAttribute("messageType", "success");
            response.sendRedirect("listado"); // redirigir a otro servlet (por url)
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            // Error al crear el usuario
            request.setAttribute("message", "Error al crear el usuario");
            request.setAttribute("messageType", "error");
            dispatchPage("/pages/alta.jsp", request, response); // devolver a una pagina (por jsp) manteniendo la misma url
        }
    }


    // metodos para validar datos

    private boolean camposVacios(String nombre, String apellido, String correo, String fechaNac, String direccion){
        return  nombre==null || apellido==null || correo==null || fechaNac==null || direccion==null ||
                 nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || fechaNac.isEmpty() ||  direccion.isEmpty();
    }
    private boolean esFormatoCorreo(String correo){
        String regexCorreo = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$";
        return correo.matches(regexCorreo);
    }

    private boolean fechaValida(LocalDate fecha){
        LocalDate hoy = LocalDate.now();
        return fecha.isEqual(hoy) || fecha.isBefore(hoy);
    }


    private boolean nombreExistenteCorreo(String correo) {      //Devuelve true si hay error
        Optional<Usuario> usuario =fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo);
        return usuario.isPresent();
    }

}