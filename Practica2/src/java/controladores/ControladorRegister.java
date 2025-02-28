/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import java.io.IOException;
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
@WebServlet(name = "ControladorRegister", urlPatterns = {"/ControladorRegister"})
public class ControladorRegister extends HttpServlet {

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
            getServletContext().getRequestDispatcher("/registro.jsp").forward(request, response);
    
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
            String email = request.getParameter("email");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String error = "";
            String pwd = request.getParameter("pwd");
            if (email == null || pwd == null || nombre == null || apellidos == null) {
                error = "Rellene todos los campos del registro.";
            } else {
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
                ServicioUsuario su = new ServicioUsuario(emf);
                boolean existe = su.buscarUsuarioPorEmail(email);
                if (!existe) {
                    Usuario usuRegistro = new Usuario ();
                    usuRegistro.setEmail(email);
                    usuRegistro.setNombre(nombre);
                    usuRegistro.setApellidos(apellidos);
                    usuRegistro.setPassword(pwd);
                    su.create(usuRegistro);
                    error = "El usuario se ha registrado correctamente";
                } else {
                    error = "El email que se desea registrar ya existe";
                }
            }
            request.setAttribute("error",error);
            getServletContext().getRequestDispatcher("/registro.jsp").forward(request, response);
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
