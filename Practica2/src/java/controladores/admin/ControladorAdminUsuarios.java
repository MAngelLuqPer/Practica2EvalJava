/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.admin;

import Utilidades.Utilidades;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Email;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioUsuario;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorAdminUsuarios", urlPatterns = {"/admin/ControladorAdminUsuarios"})
public class ControladorAdminUsuarios extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
       ServicioUsuario su = new ServicioUsuario(emf);
       if (request.getParameter("accion") == null) { //Si no se ha pulsado en editar...
       List<Usuario> listaUsuarios = su.findUsuarioEntities();
       request.setAttribute("listaUsuarios", listaUsuarios);
       getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
       } else if (request.getParameter("accion").equals("editar")){
           long id = Long.parseLong(request.getParameter("id"));
           Usuario usuEditar = su.findUsuario(id);
           request.setAttribute("usuEditar", usuEditar);
           request.getSession().setAttribute("usuEditar", usuEditar);
           getServletContext().getRequestDispatcher("/admin/editarUsuario.jsp").forward(request, response);
       } else {
           long id = Long.parseLong(request.getParameter("id"));
           String msg = "";
           try {
               su.destroy(id);
               msg = "El usuario se ha borrado correctamente.";
           } catch (NonexistentEntityException ex) {
               Logger.getLogger(ControladorAdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
               msg = "Error al borrar el usuario";
           }
           List<Usuario> listaUsuarios = su.findUsuarioEntities();
           request.setAttribute("listaUsuarios", listaUsuarios);
           request.setAttribute("msg",msg);
           getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
       }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Usuario usuEditado = (Usuario) request.getSession().getAttribute("usuEditar");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioUsuario su = new ServicioUsuario(emf);
            String msg = "";
            if (usuEditado != null) {

                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String pwd = request.getParameter("pwd");
                boolean activo = false;
                if (request.getParameter("activo") != null) {
                    activo = true;
                }
                boolean estadoAnterior = usuEditado.isActivo();
                usuEditado.setNombre(nombre);
                usuEditado.setApellidos(apellidos);
                usuEditado.setPassword(pwd);
                usuEditado.setActivo(activo);
                    if (!estadoAnterior && activo) {
                     Email email = new Email();
                     email.setTo(usuEditado.getEmail());
                     email.setSubject("Cuenta activada correctamente");
                     email.setText("Su cuenta de JavaTraveling se ha activado correctamente. ¡Disfrute de su experiencia en esta red social "+usuEditado.getNombre()+ "!");
                     email.setFrom("luque.perez.miguel.angel@iescamas.es");
                     Utilidades u = new Utilidades();
                     System.out.println("El email se ha enviado correctamente");
                    try {
                     u.enviarEmail(email, "bdty fsds qfmz oyze");
                    } catch (Throwable e) {
                        System.out.println("\"Error al enviar e-mail: <br>\" + e.getClass().getName() + \":\" + \n" +
"                                e.getMessage()");
                    }
                   }
                
                try {
                    su.edit(usuEditado);
                    msg = "Usuario editado correctamente.";
                } catch (Exception ex) {
                    msg = "Error al editar el usuario: "+ex;
                }
                request.getSession().removeAttribute("usuEditar");
            } else {
                msg = "No hay usuario que editar";
            }
            List<Usuario> listaUsuarios = su.findUsuarioEntities();
            request.setAttribute("listaUsuarios", listaUsuarios);
            request.setAttribute("msg", msg);
            getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
