<%-- 
    Document   : crearActividad
    Created on : 4 mar 2025, 18:07:14
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Creando una actividad...</title>
    </head>
    <body>
        <h1>Crear actividad</h1>
        <form method="post" enctype="multipart/form-data">
            <label for="titulo">Titulo</label>
            <input type="text" name="titulo" id="titulo" required>
            <br>
            <label for="descripcion">Descripcion</label>
            <textarea name="descripcion" id="descripcion" style="overflow:auto;resize:none" rows="5" cols="30" required></textarea>
            <br>
            <label for="fecha">Fecha</label>
            <input type="date" name="fecha" id="fecha" required>
            <br>
            <label for="imagenes">Subir imágenes</label>
            <input type="file" name="imagenes" id="imagenes" multiple accept="image/*">
             <br>
            <button type="submit">Crear actividad</button>
        </form>
    </body>
</html>
