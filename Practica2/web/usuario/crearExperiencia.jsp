<%-- 
    Document   : crearExperiencia
    Created on : 3 mar 2025, 10:59:35
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Crear nueva experiencia</title>
    </head>
    <body>
        <h1>Informacion de la experiencia</h1>
        <form method="post">
            <label for="titulo">Titulo</label>
            <input type="text" name="titulo" id="titulo">
            <br>
            <label for="descripcion">Descripcion</label>
            <textarea name="descripcion" id="descripcion" style="overflow:auto;resize:none" rows="5" cols="30"></textarea>
            <br>
            <label for="fechaInicio">Fecha de inicio</label>
            <input type="date" id="fechaInicio" name="fechaInicio" >
            <br>
            <label for="privacidad">Publico</label>
            <input type="checkbox" id="privacidad" name="privacidad">
            <br>
            <input type="submit" value="Crear experiencia">
        </form>
        <a href="${pageContext.request.contextPath}/usuario/ControladorInicio">Volver a la paágina principal</a>
        <c:if test="${not empty msg}">
            <div class="error">${msg}</div>
        </c:if>
    </body>
</html>
