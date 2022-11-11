package Tarea2.serverlets.Usuario;

import Tarea2.Logica.Clases.Usuario;
import Tarea2.Logica.Fabrica.Fabrica;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

@WebServlet(name = "Detalle", value = "/perfil")
public class Detalle extends HttpServlet {
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
        RequestDispatcher view = request.getRequestDispatcher("/pages/index.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Si no hay sesión, redirigir a login

        try {

                //HttpSession session = request.getSession();
                String correo = (String)request.getParameter("correo");

                Usuario usuario;
                // Si el usuario no viene vacio y no es mi perfil entonces buscar por nickname
                if(!correo.isEmpty() ) {

                    boolean usuarioExistePorCorreo = fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo).isPresent();
                    if (!usuarioExistePorCorreo) { // Si el usuario no existe por correo, redirigir al listado de usuarios
                        request.setAttribute("respuesta", "Usuario no encontrado");
                        response.sendRedirect("listado-usuarios");
                        return;
                    }
                    usuario = fabrica.getIUsuario().obtenerUsuarioPorCorreo(correo).get();
                    request.setAttribute("datos", usuario);

                }else{
                    dispatchPage("/pages/listado.jsp", request, response);
                }


                dispatchPage("/pages/perfil.jsp", request, response);

        } catch (RuntimeException e) {
            dispatchError("Error al obtener datos para los componentes de la pagina", request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}