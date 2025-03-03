<%-- 
    Document   : editarAct
    Created on : 3 mar 2025, 19:54:15
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="modelo.entidades.Actividad"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar Experiencia</h1>
        <form method="post">
            <label for="titulo">Titulo</label>
            <input type="text" name="titulo" id="titulo" value="${actEditar.titulo}">
            <br>
            <label for="descripcion">Descripcion</label>
            <textarea style="overflow:auto;resize:none" rows="5" cols="30" name="descripcion">${actEditar.descripcion}</textarea>
            <br>
            <label for="fecha">Fecha</label>
            
            <%
               
                Actividad actEditar = (Actividad) request.getAttribute("actEditar");
                Date fecha = actEditar.getFecha();

                String fechaFormatted = "";
                if (fecha != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    fechaFormatted = sdf.format(fecha);
                }
            %>
            
            <input type="date" name="fecha" id="fecha" value="<%= fechaFormatted %>">
            <br>
            <input type="submit" value="Guardar cambios">
        </form>
        <c:if test="${not empty msg}">
            <div class="error">${msg}</div>
        </c:if>
    </body>
</html>
