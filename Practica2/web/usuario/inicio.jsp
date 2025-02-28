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
    </head>
    <body>
        <h1>Bienvenido a la web, ${usuario.nombre}</h1>
        <c:if test="${usuario.tipo == 'admin'}">
            <a href="${pageContext.request.contextPath}/admin/ControladorAdminUsuarios">Administrar usuarios</a>
        </c:if>
    </body>
</html>
