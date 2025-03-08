<%-- 
    Document   : editarExperiencia
    Created on : 4 mar 2025, 21:12:15
    Author     : mangel
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modelo.entidades.ExperienciaViaje"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="../styles/editarExperiencia.css">
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar experiencia</h1>
        <form method="post">
            <div>
                <label for="titulo">Título:</label>
                <input type="text" id="titulo" name="titulo" value="${expEdit.titulo}" required maxlength="30">
            </div>

            <div>
                <label for="descripcion">Descripción:</label>
                <textarea id="descripcion" name="descripcion" rows="4" cols="50" required maxlength="400">${expEdit.descripcion}</textarea>
            </div>


            
            
            <div>
                <label for="fechaInicio">Fecha de inicio:</label>
                <input type="date" id="fechaInicio" name="fechaInicio" value="<fmt:formatDate value='${expEdit.fechaInicio}' pattern='yyyy-MM-dd'/>"required>
            </div>

            <div>
                <label for="publico">Público:</label>
                <input type="checkbox" id="publico" name="publico" 
                       <c:if test="${expEdit.publico == true}">
                           checked
                       </c:if>
            </div>
            <!--IDEA: Mostrar las fotos que tiene la actividad, junto a un checkbox. Si ese checkbox esta marcado, se borrara de el List<> de Imagenes
            y se modifica-
            Tambien se podra añadir multiples fotos, como en su creacion.
            
            PROBLEMA: Hay que buscar la manera de borrar la foto de la carpeta. PISTA: Buscar comando rm en Java (supongo que existe, como el mkdir).
            -->

            <div>
                <button type="submit">Guardar cambios</button>
            </div>
        </form>
            <a href="${pageContext.request.contextPath}/usuario/ControladorInicio">Volver</a>
    </body>
</html>
