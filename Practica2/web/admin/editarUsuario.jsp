<%-- 
    Document   : editarUsuario
    Created on : 28 feb 2025, 20:40:00
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Editar ${usuEditar.nombre}</title>
    </head>
    <body>
        <h1>Editar usuario ${usuEditar.nombre}</h1>
        <form method="post">
            <label for="nombre">Nombre: </label>
            <input type="text" name="nombre" value="${usuEditar.nombre}">
            <br>
            <label for="apellidos">Apellidos: </label>
            <input type="text" name="apellidos" value="${usuEditar.apellidos}">
            <br>
            <label for="pwd">Contraseña: </label>
            <input type="password" name="pwd" value="${usuEditar.password}">
            <br>
            <label for="activo">Activar: </label>
            <input type="checkbox" name="activo"
                        <c:if test="${usuEditar.activo == true}"> 
                           checked
                        </c:if>
            >
            <br>
            <input type="submit" value="Modificar usuario">
        </form>
    </body>
</html>
