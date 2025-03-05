<%-- 
    Document   : filtrarExp
    Created on : 5 mar 2025, 18:24:05
    Author     : mangel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<fmt:setBundle var="traduccion" basename="bundles.buscador"/>
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
        <c:forEach var="experiencia" items="${listadoExperiencias}">
             <c:if test="${experiencia.publico == true || experiencia.usuario == usuario}">
                <table border="1" class="tablaPrincipal">
                <thead>
                <th><fmt:message key="publicadoPor" bundle="${traduccion}"/></th>
                <th><fmt:message key="titulo" bundle="${traduccion}"/></th>
                <th><fmt:message key="desc" bundle="${traduccion}"/> </th>
                <th><fmt:message key="fechaDeInicio" bundle="${traduccion}"/></th>
                <th><fmt:message key="actividades" bundle="${traduccion}"/></th>
                </thead>
                
                <tbody>
                            <tr>
                                <td>${experiencia.usuario.nombre}</td>
                                <td>${experiencia.titulo}</td>
                                <td>${experiencia.descripcion}</td>
                                <td><fmt:formatDate value='${experiencia.fechaInicio}' pattern='yyyy-MM-dd'/></td>
                                <td>
                                    <table border="2">
                                        <thead>
                                            <th><fmt:message key="titulo" bundle="${traduccion}"/></th>
                                            <th><fmt:message key="desc" bundle="${traduccion}"/></th>
                                            <th><fmt:message key="fecha" bundle="${traduccion}"/></th>
                                            <th><fmt:message key="imagenes" bundle="${traduccion}"/></th>
                                            <c:if test="${experiencia.usuario == usuario}">
                                                <th><fmt:message key="acciones" bundle="${traduccion}"/></th>
                                            </c:if>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="actividad" items="${experiencia.actividades}">
                                                <tr>
                                                    <td>${actividad.titulo}</td>
                                                    <td>${actividad.descripcion}</td>
                                                    <td><fmt:formatDate value='${actividad.fecha}' pattern='yyyy-MM-dd'/></td>
                                                    <td>
                                                    <c:forEach var="imgs" items="${actividad.imagenes}">
                                                        <img src="${pageContext.request.contextPath}/usuario/ImagenesExperiencia/${imgs}" alt="${pageContext.request.contextPath}/usuario/ImagenesExperiencia/${imgs}" width="150" height="150"/>
                                                        <br/>
                                                    </c:forEach>
                                                    </td>
                                                    <c:if test="${experiencia.usuario == usuario || usuario.tipo == 'admin'}">
                                                        <td><a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias?id=${actividad.id}&idExp=${experiencia.id}&accion=eliminarAct"><fmt:message key="eliminar" bundle="${traduccion}"/></a> | <a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias?id=${actividad.id}&accion=editarAct"><fmt:message key="editar" bundle="${traduccion}"/> </a></td>
                                                    </c:if>
                                                </tr>
                                                
                                            </c:forEach>
                                            <c:if test="${experiencia.usuario == usuario || usuario.tipo == 'admin'}">
                                                <tr>
                                                    <td colspan="5" style="text-align: center"><a href="${pageContext.request.contextPath}/usuario/ControladorCrearAct?idExp=${experiencia.id}"><fmt:message key="aniadirAct" bundle="${traduccion}"/> </a></td>
                                                </tr>
                                            </c:if>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                 </tbody>
                 <tfoot>
                     <tr>
                         <th colspan="5" ><fmt:message key="opiniones" bundle="${traduccion}"/></th>
                     </tr>
                     <tr>
                         <th><fmt:message key="usuario" bundle="${traduccion}"/></th>
                         <th><fmt:message key="resenia" bundle="${traduccion}"/></th>
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
                         <td colspan="2" style="text-align: center"> <a href="${pageContext.request.contextPath}/usuario/ControladorCrearOpinion?idExp=${experiencia.id}"><fmt:message key="aniadirOp" bundle="${traduccion}"/></a></td>
                         <!--Permitir borrar la opinion si eres administrador o eres el propietario de dicha opinion-->
                     </tr>
                 </tfoot>
            </table>
           <c:if test="${experiencia.usuario == usuario || usuario.tipo == 'admin'}">
            <div class="enlaces">
                <a href="${pageContext.request.contextPath}/usuario/ControladorExperiencias?id=${experiencia.id}&accion=eliminarExp"><fmt:message key="eliminarExp" bundle="${traduccion}"/></a> | <a href="${pageContext.request.contextPath}/usuario/ControladorEditarExp?idExp=${experiencia.id}"><fmt:message key="editarExp" bundle="${traduccion}"/></a>
            </div>
            </c:if>
               </c:if>
            </c:forEach>
    </body>
</html>
