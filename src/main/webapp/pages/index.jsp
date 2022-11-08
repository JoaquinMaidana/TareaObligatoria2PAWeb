<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="/pages/estilos/style.css" %>
    </style>
    <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet'>
    <title>Empleados</title>
</head>
<body>
    
    <div class="section1-container">
        <div class="section1-buscar">
            <div >
                <label>Buscar</label>
                <input type="text" id="textBuscar" >
            </div>
            <div>
                <p><button id="order" onclick="ordenarTabla()">ordenar por fecha</button></p>
            </div>
            
        </div>
        <div class="section1-title">
            <h1>Empleados</h1>
        </div>
        
        <form onsubmit="event.preventDefault();onSubmitForm();" class="section1-form">
            <div>
                <label>Nombre</label>
                <input type="text" name="nombre" id="nombre" maxlength="12">
            </div>
            <div>
                <label>Apellido</label>
                <input type="text" name="apellido" id="apellido" maxlength="12">
            </div>
            <div>
                <label>Direccion</label>
                <input type="text" name="direccion" id="direccion" maxlength="50">
            </div>
            <br>
            <div>
                <label>Fecha</label>
                <input type="date" name="fecha" id="fecha" min="1920-01-01">
            </div>
            <br>
            <div>
                <label>Correo</label><label class="classValidate hide" id="labelId" >*Este campo es necesario</label>
                <input type="email" name="correo" id="correo">
            </div>
            <div class="button" >
                <input type="submit" value="Enviar">
            </div>
        </form>
        <div class="section1-tabla">
            <table class="mainTable " id="employeeList">
                <thead>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Direccion</th>
                    <th>Fecha</th>
                    <th>Correo</th>
                    <th></th>
                </thead>
                <tbody id="tbody">
        
                </tbody>
            </table>
        </div>
        
    </div>
    
    <script src="./script.js"></script>
</body>
</html>