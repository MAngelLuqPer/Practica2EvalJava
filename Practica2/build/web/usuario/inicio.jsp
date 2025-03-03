<%-- 
    Document   : inicio
    Created on : 26 feb 2025, 10:18:59
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
        <style>
            .tablaPrincipal {
                margin: 13px;
            }
        </style>
    </head>
    <body>
        <h1>Bienvenido a la web, ${usuario.nombre}</h1>
        <c:if test="${usuario.tipo == 'admin'}">
            <a href="${pageContext.request.contextPath}/admin/ControladorAdminUsuarios">Administrar usuarios</a>
        </c:if>
            <a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias"> Añadir una nueva experiencia</a>
             <c:forEach var="experiencia" items="${listadoExperiencias}">
            <table border="1" class="tablaPrincipal">
                <thead>
                <th>Publicado por... </th>
                <th>Titulo</th>
                <th>Descripcion </th>
                <th>Fecha de inicio</th>
                <th>Actividades</th>
                </thead>
                
                <tbody>
                        <c:if test="${experiencia.publico == true || experiencia.usuario.id == usuario.id}">
                            <tr>
                                <td>${experiencia.usuario.nombre}</td>
                                <td>${experiencia.titulo}</td>
                                <td>${experiencia.descripcion}</td>
                                <td>${experiencia.fechaInicio}</td>
                                <td>
                                    <table border="2">
                                        <thead>
                                            <th>Titulo</th>
                                            <th>Descripcion</th>
                                            <th>Fecha</th>
                                            <th>Imagenes</th>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="actividad" items="${experiencia.actividades}">
                                                <tr>
                                                    <td>${actividad.titulo}</td>
                                                    <td>${actividad.descripcion}</td>
                                                    <td>${actividad.fecha}</td>
                                                    <td>${actividad.imagenes}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </c:if>
                 </tbody>
                 <tfoot>
                     <tr>
                         <th colspan="5" >Opiniones</th>
                     </tr>
                     <tr>
                         <th>Usuario</th>
                         <th>Reseña</th>
                     </tr>
                     <tr>
                         <c:forEach var="opinion" items="${listadoOpiniones}">
                             <c:if test="${opinion.experiencia == experiencia}">
                                 <c:forEach var="usuario" items="${listadoUsuarios}">
                                     <c:if test="${usuario.id == opinion.usuario.id}">
                                         <td>${usuario.nombre}</td>
                                     </c:if>
                                 </c:forEach>
                                 <td>${opinion.contenido}</td>
                             </c:if>
                         </c:forEach>
                     </tr>
                 </tfoot>
            </table>
            </c:forEach>
    </body>
</html>
