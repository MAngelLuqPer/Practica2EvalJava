/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.admin;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioUsuario;

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
       if (request.getParameter("id") == null) { //Si no se ha pulsado en editar...
       List<Usuario> listaUsuarios = su.findUsuarioEntities();
       request.setAttribute("listaUsuarios", listaUsuarios);
       getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
       } else {
           long id = Long.parseLong(request.getParameter("id"));
           Usuario usuEditar = su.findUsuario(id);
           request.setAttribute("usuEditar", usuEditar);
           getServletContext().getRequestDispatcher("/admin/editarUsuario.jsp").forward(request, response);
    
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
