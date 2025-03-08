<%-- 
    Document   : estadisticas
    Created on : 6 mar 2025, 12:32:29
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../styles/editarUsuario.css">
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/highcharts-3d.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/modules/export-data.js"></script>
        <script src="https://code.highcharts.com/modules/accessibility.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Estadisticas</title>
        
       <script>
            datos = [
                    <c:forEach var="usu" items="${listaUsuarios}" varStatus="status">
                       ['${usu.nombre} ${usu.apellidos}',  ${fn:length(usu.experiencias)} ] ${status.last?"":","}     
                    </c:forEach>
            ];
                    datosExp = [
                    <c:forEach var="exp" items="${listadoExp}" varStatus="status">
                       ['${exp.titulo}',  ${fn:length(exp.actividades)} ] ${status.last?"":","}     
                    </c:forEach>
            ];
        
        </script>
        <link rel="stylesheet" type="text/css" href="../styles/grafica.css">
    </head>
    <body>
        <h1>Estadisticas de los usuarios</h1>
       <br>
       <a href="../usuario/ControladorInicio">Volver</a>
       <figure class="highcharts-figure">
            <div id="container"></div>
            <p class="highcharts-description">
                Grafica con los usuarios y la cantidad de experiencias que tienen.
            </p>
        </figure>
       
       <form method="GET">
            <label for="fechaInicio">Fecha Inicio:</label>
            <input type="date" id="fechaInicio" name="fechaInicio" required>
            
            <label for="fechaFin">Fecha Fin:</label>
            <input type="date" id="fechaFin" name="fechaFinal" required>

            <button type="submit">Filtrar</button>
        </form>
        <c:if test="${not empty msg}">
            <div class="fechas">${msg}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <figure class="highcharts-figure">
            <div id="containerExp"></div>
            <p class="highcharts-description">
                Grafica con las experiencias y cuantas actividades tienen.
            </p>
        </figure>
        <script src="../js/grafica.js"></script>
        <script src="../js/graficaActividades.js"></script>

    </body>
</html>
