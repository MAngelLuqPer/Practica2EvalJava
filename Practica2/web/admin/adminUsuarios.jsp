<%-- 
    Document   : adminUsuarios
    Created on : 28 feb 2025, 19:40:09
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Panel de administracion</title>
    </head>
    <body>
        <h1>Administración de usuarios</h1>
         <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Apellidos</th>
                    <th>Email</th>
                    <th>Activo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
        <c:forEach var="usuarioTable" items="${listaUsuarios}">
            <tr>
                <td>${usuarioTable.id}</td>
                <td>${usuarioTable.nombre}</td>
                <td>${usuarioTable.apellidos}</td>
                <td>${usuarioTable.email}</td>
                <td> <c:choose>
                        <c:when test="${usuarioTable.activo == true}">
                            Si
                        </c:when>
                        <c:otherwise>
                            No
                        </c:otherwise>
                    </c:choose> 
                </td>
                <td><a href="${pageContext.request.contextPath}/admin/ControladorAdminUsuarios?id=${usuarioTable.id}&accion=editar">Editar</a>
                    
                    <c:if test="${fn:length(usuarioTable.experiencias) eq 0}">
                        <a href="${pageContext.request.contextPath}/admin/ControladorAdminUsuarios?id=${usuarioTable.id}&accion=eliminar">Eliminar</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
            </tbody>
         </table>
        <c:if test="${not empty msg}">
            <div class="error">${msg}</div>
        </c:if>
    </body>
</html>
