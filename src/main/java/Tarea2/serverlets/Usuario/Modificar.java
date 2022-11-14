package Tarea2.serverlets.Usuario;

import Tarea2.Logica.Clases.Usuario;
import Tarea2.Logica.Fabrica.Fabrica;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

@WebServlet(name = "Modificar", value = "/modificar")
@MultipartConfig
public class Modificar extends HttpServlet {

    Fabrica fabrica;

    public void init() {
        fabrica = Fabrica.getInstance();
    }

    protected void dispatchPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher view = request.getRequestDispatcher(page);
        view.forward(request, response);
    }



    protected void dispatchError(String errorMessage, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("message", errorMessage);
        request.setAttribute("messageType","error");
        RequestDispatcher view = request.getRequestDispatcher("/pages/espectaculo/registro-espectaculo.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Si no hay sesión, redirigir a login

        try {
                String correo = (String) request.getParameter("correo");

                Map<String, Usuario> todosUsuarios = fabrica.getIUsuario().obtenerUsuarios();
                request.setAttribute("todosUsuarios", todosUsuarios);
                Optional<Usuario> usuario = fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo);


                if(usuario.isPresent())
                    request.setAttribute("usuario", usuario.get());

                dispatchPage("/pages/modificar.jsp", request, response);


        } catch (RuntimeException e) {
            dispatchError("Error al obtener datos para los componentes de la pagina", request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usu=null;
        String correo = request.getParameter("correo");
        if(fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo).isPresent()) {
            usu = fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo).get();

        }else{
            dispatchError("Correo no valido", request, response);
            return;
        }



        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String direccion = request.getParameter("direccion");
        String fechaNac_str = request.getParameter("fechaNac");
        Part part=request.getPart("imagen");

        System.out.println(nombre);
        System.out.println(apellido);
        System.out.println(direccion);
        System.out.println(fechaNac_str);
        System.out.println(part);

        // Validar los datos traidos del formulario:

        //error cuando alguno de los campos son vacios
        if(camposVacios(nombre, apellido, fechaNac_str, direccion)) {
            dispatchError("Los campos obligatorios no pueden ser vacios", request, response);
            return;
        }

        LocalDate fechaNac = LocalDate.parse(fechaNac_str); // ahora que sabemos que no es vacio, lo podemos parsear

        //La fecha no es valida porque no nacio mañana
        if(!fechaValida(fechaNac)){
            dispatchError("La fecha no es valida", request, response);
            return;
        }

        String urlImagen="";
        if(part.getSize()!=0){
            InputStream inputImagen=part.getInputStream();
            urlImagen= Fabrica.getInstance().getIDatabase().guardarImagen((FileInputStream) inputImagen);
        }
        if(usu!=null){
            usu.setNombre(nombre);
            usu.setApellido(apellido);
            usu.setFechaNacimiento(fechaNac);
            if(!urlImagen.equals(""))
                usu.setImagen(urlImagen);
        }

        try {
            // Se crea el usuario en la base de datos
            fabrica.getIUsuario().modificarUsuario(usu);

            // Redireccionar a la pantalla de login
            request.getSession().setAttribute("message", "Informacion modificada exitosamente");
            System.out.println("Se modifico el user");
            response.sendRedirect("perfil?correo="+usu.getCorreo()); // redirijir a un servlet (por url)
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            // Error al crear el usuario
            dispatchError("Error al crear el usuario", request, response); // devolver a una pagina (por jsp) manteniendo la misma url
            return;
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usu=null;
        String correo = request.getParameter("correo");
        if(fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo).isPresent()) {
            usu = fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo).get();
            System.out.println("llego al put y el usu es:"+usu);

        }else{
            dispatchError("Correo no valido", request, response);
            return;
        }

        String body = inputStreamToString(request.getInputStream());
        System.out.println("body: " + body);
        System.out.println("el correo es"+correo);


        String separador = ",";
        String[] partes = body.split(separador);
        String[] datos =  new String[5];
        int i=0;
        for (String part: partes) {
            //System.out.println(part);
            datos[i]=part;
            i++;
        }
        String nombre = "";
        String fechaNac_str="";
        System.out.println(datos[0]);
        nombre = datos[0];
        String apellido = datos[1];
        fechaNac_str = datos[2];
        String direccion =datos[3] ;
        //Part part=request.getPart("");


        System.out.println(nombre);


        //System.out.println("nomr"+nombre);
        System.out.println("el apellido es"+apellido);
        System.out.println(direccion);
        System.out.println(fechaNac_str);
        //System.out.println(part);

        // Validar los datos traidos del formulario:

        //error cuando alguno de los campos son vacios
        if(camposVacios(nombre, apellido, fechaNac_str, direccion)) {
            dispatchError("Los campos obligatorios no pueden ser vacios", request, response);
            return;
        }

        LocalDate fechaNac = LocalDate.parse(fechaNac_str); // ahora que sabemos que no es vacio, lo podemos parsear

        //La fecha no es valida porque no nacio mañana
        if(!fechaValida(fechaNac)){
            dispatchError("La fecha no es valida", request, response);
            return;
        }

        String urlImagen="";
    //    if(part.getSize()!=0){
    //        InputStream inputImagen=part.getInputStream();
    //        urlImagen= Fabrica.getInstance().getIDatabase().guardarImagen((FileInputStream) inputImagen);
    //    }
        if(usu!=null){
            usu.setNombre(nombre);
            usu.setApellido(apellido);
            usu.setFechaNacimiento(fechaNac);
            if(!urlImagen.equals(""))
                usu.setImagen(urlImagen);
        }

        try {
            // Se crea el usuario en la base de datos
            fabrica.getIUsuario().modificarUsuario(usu);

            // Redireccionar a la pantalla de login
            request.getSession().setAttribute("message", "Informacion modificada exitosamente");
            System.out.println("Se modifico el user");
           // response.sendRedirect("perfil?correo="+usu.getCorreo()); // redirijir a un servlet (por url)
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
            // Error al modificarr el usuario
            dispatchError("Error al modificar el usuario", request, response); // devolver a una pagina (por jsp) manteniendo la misma url
            return;
        }
    }

    private static String inputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        return scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
    }




    private boolean camposVacios(String nombre, String apellido, String fechaNac, String direccion){
        return  nombre==null || apellido==null  || fechaNac==null || direccion==null ||
                nombre.isEmpty() || apellido.isEmpty()  || fechaNac.isEmpty() ||  direccion.isEmpty();
    }
    private boolean fechaValida(LocalDate fecha){
        LocalDate hoy = LocalDate.now();
        return fecha.isEqual(hoy) || fecha.isBefore(hoy);
    }

}
