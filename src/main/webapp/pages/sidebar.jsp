<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% //CARGAR ALGUN@S ESPECTACULOS / FUNCIONES / PAQUETES / ARTISTA

    Map<String, Usuario> usuarioMap = request.getAttribute("todosUsuarios") != null ? (Map<String, Usuario>) request.getAttribute("todosUsuarios") : new HashMap<>();

    // shuffle usuarios and filter up to 5
    List<String> todosUsuarios = new ArrayList<>(usuarioMap.keySet());
    Collections.shuffle(todosUsuarios);
    todosUsuarios = todosUsuarios.subList(0, Math.min(5, todosUsuarios.size()));

%>
<div class="sidebars">

    <div class="sidebar">
        <div class="sidebar__item">
            <h4>Usuarios</h4>
            <ul>
                <% for (String usu : todosUsuarios) { %>
                <%-- TODO: LLEVAR A LISTADO DE ESPECTACULOS POR CATEGORIA --%>
                <li><a href="perfil?nickname=<%=usu%>"><%=usu%></a></li>
                <hr>
                <% } %>
            </ul>
        </div>
    </div>
</div>