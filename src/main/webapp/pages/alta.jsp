<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%  // Cargamos el usuarioLogueado en cada pantalla

    String message = request.getAttribute("message") instanceof String ? (String) request.getAttribute("message") : "";
    String messageType = request.getAttribute("messageType") instanceof String ? (String) request.getAttribute("messageType") : "";



    String nombre = "";
    String apellido = "";
    String correo = "";
    String fechaNac = "";
    String imagen = "";
    String direccion = "";



    //Traer datos precargados del request anterior
    if(messageType.equals("error")) {
        nombre = request.getParameter("nombre") != null ? request.getParameter("nombre") : "";
        apellido = request.getParameter("apellido") != null ? request.getParameter("apellido") : "";
        correo = request.getParameter("correo") != null ? request.getParameter("correo") : "";
        fechaNac = request.getParameter("fechaNac") != null ? request.getParameter("fechaNac") : "";
        imagen = request.getParameter("imagen") != null ? request.getParameter("imagen") : "";
        direccion = request.getParameter("direccion") != null ? request.getParameter("direccion") : "";

    }
%>
<!DOCTYPE html>
<html>
<head>
    <style><%@ include file="/pages/estilos/global.css" %></style>
    <style><%@ include file="/pages/estilos/registro.css" %></style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CoronaTicketsUY</title>
</head>
<body>
<div class="registro-container">
    <img src="https://i.imgur.com/d6cWesT.jpeg" alt="background_img"  />
    <div class="background_container">

        <div id="message" class="hidden <%=messageType%>" role="alert">
            <%=message%>
        </div>

        <div class="container">
            <h3>Registrate creando tu usuario</h3>
            <form id="idform" name="myform" method="POST" action="alta" enctype="multipart/form-data">

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
                        <label class="subtitulos" for="correo">Correo</label>
                        <input type="email" id="correo" name="correo" placeholder="*Correo..." maxlength="50" value="<%= correo%>">
                    </div>

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

                        <div class="input-container">
                            <label class="subtitulos" for="direccion">Descripcion</label>
                            <textarea id="direccion" name="direccion" placeholder="*Direccion..." maxlength="100"
                                      value="<%= direccion%>"><%= direccion%></textarea>
                        </div>
                    </div>
                </div>

                <button id="submitBtn" type="button" onclick="enviarForm()">Registrarse!</button>
            </form>

        </div>
    </div>
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

    function validarCamposVacios() {

        const NOMBRE = $("#nombre").val().trim()
        const APELLIDO = $("#apellido").val().trim()
        const CORREO = $("#correo").val().trim()
        const FECHA_NAC = $("#fechaNac").val().trim()
        const DiRECCION = $("#direccion").val().trim();


        if (NOMBRE == "") {
            mensaje("El campo 'Nombre' no puede estar vacio");
            NOMBRE.addClass("invalid-input");
            return false;
        }
        if (APELLIDO == "") {
            mensaje("El campo 'Apellido' no puede estar vacio");
            APELLIDO.addClass("invalid-input");
            return false;
        }
        if (CORREO == "") {
            mensaje("El campo 'Correo' no puede estar vacio");
            CORREO.addClass("invalid-input");
            return false;
        }
        if (DiRECCION == "") {
            mensaje("El campo 'Direccion' no puede estar vacio");
            CORREO.addClass("invalid-input");
            return false;
        }

        if (FECHA_NAC == "") {
            mensaje("El campo 'Fecha de nacimiento' no puede estar vacio");
            FECHA_NAC.addClass("invalid-input");
            return false;
        }
        return true;
    }

    function validarFormatoCorreo() {
        const CORREO = $("#correo").val().trim()
        const PATTERN = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$"

        if (!CORREO.match(PATTERN)) {
            mensaje("El campo 'Correo' no tiene un formato valido");
            CORREO.addClass("invalid-input");
            return false;
        }
        return true;
    }

    function validarFechaNac() {
        const FECHA_NAC = $("#fechaNac");

        if (new Date(fechaNac) > new Date()) {
            mensaje("La fecha de nacimiento no puede ser mayor a la fecha actual");
            FECHA_NAC.addClass("invalid-input");
            return false;
        }
        return true;
    }


    function enviarForm() {
        let SUBMITBUTTON = $("#submitBtn");
        SUBMITBUTTON.prop("disabled", true);

        let formularioValido = validarCamposVacios() &&  validarFormatoCorreo() && validarFechaNac();


        //Enviar formulario con jquery
        if (formularioValido) {
            document.getElementById("idform").submit();
        }
    }
</script>
</body>
</html>
