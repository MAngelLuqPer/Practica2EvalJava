<%-- 
    Document   : adminUsuarios
    Created on : 28 feb 2025, 19:40:09
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <c:forEach var="usuario" items="${listaUsuarios}">
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.apellidos}</td>
                <td>${usuario.email}</td>
                <td> <c:choose>
                        <c:when test="${usuario.activo == true}">
                            Si
                        </c:when>
                        <c:otherwise>
                            No
                        </c:otherwise>
                    </c:choose> 
                </td>
                <td><a href="${pageContext.request.contextPath}/admin/ControladorAdminUsuarios?id=${usuario.id}">Editar</a></td><button>Eliminar</button>  </td>
            </tr>
        </c:forEach>
            </tbody>
         </table>
    </body>
</html>
