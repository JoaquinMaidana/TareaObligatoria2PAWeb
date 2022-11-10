<%--
  Created by IntelliJ IDEA.
  User: esteban.rosano
  Date: 12/10/2022
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<div class="header">
    <div class="header__top">
        <div class="header__left">
            <h1>Tarea obligatoria 2</h1>
        </div>
        <div class="header__right" onclick="toggleMenu()">
            <div class="vertical">|</div>

        </div>
        <div class="header__right__submenu_wrap">
            <div class="header__right__submenu">
                <div class="header__right__submenu__userinfo">

                    <div class="header__right__submenu__userinfo__text">



                    </div>
                </div>
                <hr>
                <%-- PERFIL USUARIO --%>
                <div class="header__right__submenu__options">
                    <a href="perfil">Mi perfil</a>
                    <span>></span>
                </div>
                <hr>


                <div class="header__right__submenu__options">
                    <a href="registro-espectadores-a-funcion">Registrarse a funcion</a>
                    <span>></span>
                </div>


                <hr>
                <%-- CERRAR SESION --%>
                <div class="header__right__submenu__options">
                    <p onclick="cerrarSesion()">Cerrar sesion</p>
                    <span>></span>
                </div>
                <form id="cerrarSesionForm" method="POST" action="home" hidden>
                    <button type="submit"></button>
                </form>
            </div>
        </div>
    </div>
    <div class="header__center">
        <div class="header__left">
            <ul>
                <li><a href="listado">Listado Usuarios</a></li>
                <li><a href="listado-espectaculos">Nuevo Usuario</a></li>
                <li><a href="listado-funciones">Funciones</a></li>

            </ul>
        </div>
        <div class="header__right">
            <div class="header__right__search">
                <input type="text" placeholder="Buscar espectaculo, paquete, y más...">
                <button type="submit">
                    <img src="https://i.imgur.com/wkx2zCs.png" alt="search">
                </button>
            </div>
        </div>
    </div>
</div>

<%--Javascript--%>
<script>
    function redirigirHome(){
        window.location.href = "home";
    }
    function toggleMenu(){
        let menu = document.querySelector(".header__right__submenu_wrap");
        menu.classList.toggle("open-menu");
    }
    function cerrarSesion() {
        document.getElementById("cerrarSesionForm").submit();
    }
</script>