<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="Tarea2.Logica.Clases.Usuario" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%  // Cargamos el usuarioLogueado en cada pantalla


    String message = request.getAttribute("message") instanceof String ? (String) request.getAttribute("message") : "";
    String messageType = request.getAttribute("messageType") instanceof String ? (String) request.getAttribute("messageType") : "";

    Usuario usuario = (Usuario) request.getAttribute("usuario");

    //Traer datos precargados del request anterior
    String nombre = "";
    String apellido = "";
    String fechaNac = "";
    String imagen = "";
    String direccion = "";
    String correo ="";

    nombre = request.getAttribute("nombre") instanceof String ? (String) request.getAttribute("nombre") : usuario.getNombre();
    apellido = request.getAttribute("apellido") instanceof String ? (String) request.getAttribute("apellido") : usuario.getApellido();
    fechaNac = request.getAttribute("fechaNac") instanceof String ? (String) request.getAttribute("fechaNac") : usuario.getFechaNacimiento().toString();
    imagen = request.getAttribute("imagen") instanceof String ? (String) request.getAttribute("imagen") : usuario.getImagen();
    direccion = request.getAttribute("direccion") instanceof String ? (String) request.getAttribute("cdireccion") : usuario.getDireccion();
    correo = request.getAttribute("correo") instanceof String ? (String) request.getAttribute("correo") : usuario.getCorreo();


%>
<!DOCTYPE html>
<html>
<head>
    <style>
        <%@ include file="/pages/estilos/global.css" %>
    </style>
    <style><%@ include file="/pages/estilos/registro.css" %></style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CoronaTicketsUY</title>
</head>
<body>
<div class="background_container">
    <div id="message" class="hidden <%=messageType%>" role="alert">
        <%=message%>
    </div>

    <main class="coronaTicketsUY">
        <%@ include file="/pages/header.jsp" %>
        <div class="page-title">
            <h3>Modificar Perfil</h3>
        </div>
        <section>
            <%@ include file="/pages/sidebar.jsp" %>
            <div class="main-container">
                <%-- AGREGAR COMPONENTES ABAJO--%>
                <form id="idform" name="myform"   enctype="multipart/form-data">
                    <div id="camposComunes">
                        <div class="input-group-container">
                            <div class="input-container">
                                <label class="subtitulos" for="nombre">Nombre</label>
                                <input type="text" id="nombre" name="nombre" placeholder="*Nombre..." maxlength="30" value="<%= nombre%>">
                            </div>
                            <div class="input-container">
                                <label class="subtitulos" for="apellido">Apellido</label>
                                <input type="text" id="apellido" name="apellido" placeholder="*Apellido..." maxlength="30" value="<%= apellido%>">
                            </div>
                        </div>
                        <div class="input-container">
                            <label class="subtitulos" for="direccion">Direccion</label>
                            <input type="text" id="direccion" name="direccion" placeholder="*Direccion..." maxlength="30"
                                   value="<%= direccion%>">
                        </div>
                        <div class="input-container">
                            <label class="subtitulos" for="correo" >Correo</label>
                            <input type="email" id="correo" name="correo" placeholder="*Correo..." maxlength="30"
                                   value="<%= correo%>" readonly>
                        </div>
                        //enviar correo por parametro al post
                        <div class="input-group-container">
                            <div class="input-container">
                                <label class="subtitulos" for="fechaNac">Fecha de nacimiento</label>
                                <input type="date" id="fechaNac" name="fechaNac" placeholder="*Fecha de nacimiento..."
                                       max="<%= LocalDate.now().toString() %>" value="<%= fechaNac%>">
                            </div>
                            <div class="input-container">
                                <label class="subtitulos" for="imagen">Imagen de perfil</label>
                                <input type="file" accept="image/*" id="imagen" name="imagen" value="<%= imagen%>">
                            </div>
                        </div>
                    </div>

                    <button id="submitBtn" type="button" onclick="enviarForm()">Registrarse!</button>
                </form>
                <%-- AGREGAR COMPONENTES ARRIBA--%>
            </div>
        </section>
    </main>
</div>

<%--    Javascript    --%>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {
        const MESSAGE = $("#message");

        if (MESSAGE.text().trim() != "") {
            MESSAGE.removeClass("hidden");
            setTimeout(() => {
                MESSAGE.text("");
                MESSAGE.addClass("hidden");
            }, 5000);
        } else {
            MESSAGE.addClass("hidden");
        }
    });

    function mensaje(msg) {
        const SUBMITBUTTON = $("#submitBtn");
        const MESSAGE = $("#message");
        MESSAGE.text(msg);
        MESSAGE.addClass("error");
        MESSAGE.removeClass("hidden");

        setTimeout(() => {
            MESSAGE.text("");
            MESSAGE.addClass("hidden");
            MESSAGE.removeClass("error");
        }, 5000);

        SUBMITBUTTON.prop("disabled", false);
    }
    //TODO: HOMOLOGAR MENSAJES DE VALICACION

    function enviarForm() {
        let SUBMITBUTTON = $("#submitBtn");
        SUBMITBUTTON.prop("disabled", true);

        //Obtener inputs con jquery
        let nombre = $("input[name='nombre']").val();
        let apellido = $("input[name='apellido']").val();
        let fechaNac = $("input[name='fechaNac']").val();
        let direccion = $("input[name='direccion']").val();
        let correo = $("input[name='correo']").val();
        let imagen = $("input[name='imagen']").val();

        let formularioValido = true;

        // Validar campos vacios comunes
        if ( nombre === "" || apellido === "" || fechaNac === "" || direccion === "" || correo === "") {
            alert("Complete todos los campos obligatorios");
            formularioValido = false;
            return;
        }


        //validar fecha nacimiento menor a hoy y que no haya nacido en el siglo 19 para abajo
        if ((new Date(fechaNac) > new Date()) || (new Date(fechaNac).getFullYear() < 1900)){
            alert("Fecha no valida");
            formularioValido = false;
            return;
        }

        let cadena = nombre+","+apellido+","+fechaNac+","+direccion+","+imagen;
        //Enviar formulario con jquery
        if (formularioValido) {
            document.getElementById("idform").submit();

            $.ajax({
                type : 'PUT',
                //url : url,
                contentType: 'application/json',
                data : cadena,
                success : function(data, status, xhr){
                    console.log(data);
                    window.location.replace("perfil?correo="+correo);
                },
                error: function(xhr, status, error){
                    //$('#msg').html('<span style=\'color:red;\'>'+error+'</span>')
                    console.log("hubo error");
                }
            });
        }
        else {
            alert("EL FORMULARIO NO SE ENVIO POR INVALIDO")
            const SUBMITBUTTON = $("#submitBtn");
            SUBMITBUTTON.prop("disabled", false);
        }
    }


</script>
</body>
</html>