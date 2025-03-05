<%-- 
    Document   : inicio
    Created on : 26 feb 2025, 10:18:59
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>JSP Page</title>
        <style>
            .enlaces {
                margin-bottom: 25px;
            }
        </style>
    </head>
    <body>
        <!-- Rediseñar para implementar header y un cerrar sesion -->
        <h1>Bienvenido a la web, ${usuario.nombre}</h1>
        <c:if test="${not empty msg}">
                    <div class="error">${msg}</div>
        </c:if>
                    <p>${usuario.tipo}</p>
        <c:if test="${usuario.tipo == 'admin'}">
            <a href="${pageContext.request.contextPath}/admin/ControladorAdminUsuarios">Administrar usuarios</a>
        </c:if>
            <a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias"> Añadir una nueva experiencia</a>
            <a href="${pageContext.request.contextPath}/usuario/ControladorMostrarFiltrado">Buscar publicaciones</a>
             <c:forEach var="experiencia" items="${listadoExperiencias}">
             <c:if test="${experiencia.publico == true || experiencia.usuario == usuario}">
                <table border="1" class="tablaPrincipal">
                <thead>
                <th>Publicado por... </th>
                <th>Titulo</th>
                <th>Descripcion </th>
                <th>Fecha de inicio</th>
                <th>Actividades</th>
                </thead>
                
                <tbody>
                            <tr>
                                <td>${experiencia.usuario.nombre}</td>
                                <td>${experiencia.titulo}</td>
                                <td>${experiencia.descripcion}</td>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${experiencia.fechaInicio}"/></td>
                                <td>
                                    <table border="2">
                                        <thead>
                                            <th>Titulo</th>
                                            <th>Descripcion</th>
                                            <th>Fecha</th>
                                            <th>Imagenes</th>
                                            <c:if test="${experiencia.usuario == usuario}">
                                                <th>Acciones</th>
                                            </c:if>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="actividad" items="${experiencia.actividades}">
                                                <tr>
                                                    <td>${actividad.titulo}</td>
                                                    <td>${actividad.descripcion}</td>
                                                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${actividad.fecha}"/></td>
                                                    <td>
                                                    <c:forEach var="imgs" items="${actividad.imagenes}">
                                                        <img src="${pageContext.request.contextPath}/usuario/ImagenesExperiencia/${imgs}" alt="${pageContext.request.contextPath}/usuario/ImagenesExperiencia/${imgs}" width="150" height="150"/>
                                                        <br/>
                                                    </c:forEach>
                                                    </td>
                                                    <c:if test="${experiencia.usuario == usuario || usuario.tipo == 'admin'}">
                                                        <td><a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias?id=${actividad.id}&idExp=${experiencia.id}&accion=eliminarAct">Eliminar</a> | <a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias?id=${actividad.id}&accion=editarAct">Editar </a></td>
                                                    </c:if>
                                                </tr>
                                                
                                            </c:forEach>
                                            <c:if test="${experiencia.usuario == usuario || usuario.tipo == 'admin'}">
                                                <tr>
                                                    <td colspan="5" style="text-align: center"><a href="${pageContext.request.contextPath}/usuario/ControladorCrearAct?idExp=${experiencia.id}">Añadir actividad </a></td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                 </tbody>
                 <tfoot>
                     <tr>
                         <th colspan="5" >Opiniones</th>
                     </tr>
                     <tr>
                         <th>Usuario</th>
                         <th>Reseña</th>
                     </tr>
                         <c:forEach var="opinion" items="${listadoOpiniones}">
                            <tr>
                             <c:if test="${opinion.experiencia == experiencia}">
                                 <c:forEach var="usuario" items="${listadoUsuarios}">
                                     <c:if test="${usuario.id == opinion.usuario.id}">
                                         <td>${usuario.nombre}</td>
                                     </c:if>
                                 </c:forEach>
                                 <td>${opinion.contenido}</td>
                             </c:if>
                           </tr>
                         </c:forEach>

                     <tr>
                         <td colspan="2" style="text-align: center"> <a href="${pageContext.request.contextPath}/usuario/ControladorCrearOpinion?idExp=${experiencia.id}">Añadir opinion</a></td>
                         <!--Permitir borrar la opinion si eres administrador o eres el propietario de dicha opinion-->
                     </tr>
                 </tfoot>
            </table>
           <c:if test="${experiencia.usuario == usuario || usuario.tipo == 'admin'}">
            <div class="enlaces">
                <a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias?id=${experiencia.id}&accion=eliminarExp">Eliminar experiencia</a> | <a href="${pageContext.request.contextPath}/usuario/ControladorEditarExp?idExp=${experiencia.id}">Editar experiencia</a>
            </div>
            </c:if>
               </c:if>
            </c:forEach>
    </body>
</html>
