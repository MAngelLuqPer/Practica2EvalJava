<%-- 
    Document   : registro
    Created on : 26 feb 2025, 10:52:38
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Registrar usuario</title>
    </head>
    <body>
        <h1>Registrarse</h1>
        <form method="post">
            <label for="email">Email:</label>
            <input type="email" name="email" required>
            <br>
            <label for="nombre">Nombre: </label>
            <input type="text" name="nombre" min="4" max="20" required>
            <br>
            <label for="apellidos">Apellidos: </label>
            <input type="text" name="apellidos" min="4" max="20" required>
            <br>
            <label for="pwd">Contraseña: </label>
            <input type="password" name="pwd" required  min="4" max="30">
            <br>
            <input type="submit" value="Registrar cuenta">
        </form>
        <a href="${pageContext.request.contextPath}/ControladorLogin">Identificarse</a>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
    </body>
</html>
