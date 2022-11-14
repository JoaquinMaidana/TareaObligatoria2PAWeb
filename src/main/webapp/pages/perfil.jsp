<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="Tarea2.Logica.Clases.Usuario" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%  // Cargamos el usuarioLogueado en cada pantalla


    String message = request.getAttribute("message") instanceof String ? (String) request.getAttribute("message") : "";
    String messageType = request.getAttribute("messageType") instanceof String ? (String) request.getAttribute("messageType") : "";

    Usuario usuario = (Usuario) request.getAttribute("datos");

    //String json = new Gson().toJson(usuario);

%>
<html>
<head>
    <title>Perfil Usuario</title>
    <style>
        <%@ include file="/pages/estilos/global.css" %>
    </style>
    <style>
        <%@ include file="/pages/estilos/detalles.css" %>
    </style>
</head>
<body>
<div class="background_container">
    <div id="message" class="hidden <%=messageType%>" role="alert">
        <%=message%>
    </div>

    <main class="coronaTicketsUY">
        <%@ include file="/pages/header.jsp" %>
        <div class="page-title">
            <h3>Detalle de usuario</h3>
        </div>
        <section>

            <div class="grid-container">
                <%-- AGREGAR COMPONENTES ABAJO--%>
                <img src="<%=usuario.getImagen()%>" alt="Foto de perfil" class="img_perfil">
                <div class="first-data">
                    <h4><%=usuario.getNombre()+" "+usuario.getCorreo()%></h4>
                    <div class="buttons">
                        <a class="btn" href="modificar?correo=<%=usuario.getCorreo()%>">Modificar Usuario</a>
                        <a class="btn" id="btn_desactivar">Desactivar Usuario</a>
                    </div>


                </div>
                <div class="tabs">
                    <div class="menu">
                        <p data-target="#datos_generales" class="active">Datos</p>

                    </div>

                    <div class="content">
                        <div data-content id="datos_generales" class="active">
                            <h4>Nombre:<%=usuario.getNombre()%></h4>
                            <h4>Apellido:<%=usuario.getApellido()%></h4>
                            <h4>Correo:<%=usuario.getCorreo()%></h4>
                            <h4>Direccion:<%=usuario.getDireccion()%></h4>
                            <h4>Fecha de Nacimiento:<%=usuario.getFechaNacimiento()%></h4>

                        </div>

                    </div>
                </div>
                <%-- AGREGAR COMPONENTES ARRIBA--%>
            </div>
        </section>
    </main>
</div>

<%--    Javascript--%>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>

    $(document).ready(function () {
        let TABS = document.querySelectorAll('[data-target]');
        let CONTENTS = document.querySelectorAll('[data-content]');

        // Agregar evento click a cada tab
        TABS.forEach(TAB => {
            TAB.addEventListener('click', (e) => {
                // Remover la clase active de todos los tabs y contents para luego agregarla al tab y content seleccionado
                CONTENTS.forEach(content => {
                    content.classList.remove('active');
                })
                TABS.forEach(tab => {
                    tab.classList.remove('active');
                })

                let selectedTab = e.target;
                selectedTab.classList.add('active');
                let selectedContent = document.querySelector(selectedTab.dataset.target);
                selectedContent.classList.add('active');
            })
        })
    });

    function desactivar_usuario(){
        const http = new XMLHttpRequest();
        const url ="";
        http.open("DELETE","perfil?correo=<%=usuario.getCorreo()%>");
        http.onreadystatechange = function (){
            if(http.status==200 && http.readyState===XMLHttpRequest.DONE){
                console.log("Desactivado")
                window.location.href = "listado";
            }
            else{
                console.log("No desactivado")
            }
        }

        http.send();
    }
    document.getElementById("btn_desactivar").addEventListener("click",function (){
        desactivar_usuario();
    });


</script>
</body>
</html>