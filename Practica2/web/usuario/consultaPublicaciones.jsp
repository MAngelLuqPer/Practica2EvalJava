<%-- 
    Document   : filtrarPublicaciones
    Created on : 5 mar 2025, 12:38:38
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!--Importamos el .properties con todas sus keys y traducciones-->
<fmt:setBundle var="traduccion" basename="bundles.buscador"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../styles/consultaPublicaciones.css">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Filtrar experiencias</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script src="../js/filtrarExp.js"></script>
    </head>
  <body onload="filtrar()">
      <!--Uso de fmt:message para mostrar un texto u otro dependiendo del idioma del navegador-->
        <h1><fmt:message key="tituloBuscar" bundle="${traduccion}"/></h1>
        <br>
        <div>
            <label><fmt:message key="textLabel" bundle="${traduccion}"/></label>
            <input type="text" name="filtro" id="filtro" onkeyup="filtrar()">
        </div>
            <a href="../usuario/ControladorInicio"><fmt:message key="volver" bundle="${traduccion}"/></a>
        <div id="listado">
        </div>
        <br>
        
    </body>
</html>
