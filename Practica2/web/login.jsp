<%-- 
    Document   : login
    Created on : 26 feb 2025, 10:11:26
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
        <h1>Iniciar sesion</h1>
        <br>
        <form method="post">
            <label>e-mail</label>
            <input type="email" name="email"  required="">
            <br>
            <label>Contraseña</label>
            <input type="password" name="pwd" required="">
            <br>
            <input type="submit" value="Iniciar Sesión">
        </form>
        <a href="${pageContext.request.contextPath}/ControladorRegister">¿No tienes cuenta? Registrese</a>
        <br>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
    </body>
</html>
