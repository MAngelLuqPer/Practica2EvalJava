<%-- 
    Document   : crearOpinion
    Created on : 4 mar 2025, 20:46:50
    Author     : mangel
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../styles/crearOpinion.css">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Añadir opinion</h1>
        <form method="post">
            <label for="comentario">Comentario:</label>
            <textarea style="overflow:auto;resize:none" rows="5" cols="30" name="comentario" required></textarea>
            <br>
            <input type="submit" value="Publicar comentario">
        </form>
        <a href="${pageContext.request.contextPath}/usuario/ControladorInicio">Volver</a>
    </body>
</html>
