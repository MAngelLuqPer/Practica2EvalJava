<%-- 
    Document   : editarAct
    Created on : 3 mar 2025, 19:54:15
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="modelo.entidades.Actividad"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../styles/editarAct.css">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar Experiencia</h1>
        <form method="post">
            <label for="titulo">Titulo</label>
            <input type="text" name="titulo" id="titulo" value="${actEditar.titulo}" required>
            <br>
            <label for="descripcion">Descripcion</label>
            <textarea style="overflow:auto;resize:none" rows="5" cols="30" name="descripcion" required>${actEditar.descripcion}</textarea>
            <br>
            <label for="fecha">Fecha</label>
            <!--uso de fmt-formatdate para mostrar la fecha de una forma u otra-->
            <input type="date" name="fecha" id="fecha" value="<fmt:formatDate value='${actEditar.fecha}' pattern='yyyy-MM-dd'/>" required>
            <br>
            <input type="submit" value="Guardar cambios">
        </form>
            <a href="${pageContext.request.contextPath}/usuario/ControladorInicio">Volver</a>
        <c:if test="${not empty msg}">
            <div class="error">${msg}</div>
        </c:if>
    </body>
</html>
