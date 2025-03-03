/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.usuario;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Opinion;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioOpinion;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorInicio", urlPatterns = {"/usuario/ControladorInicio"})
public class ControladorInicio extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            ServicioOpinion so = new ServicioOpinion(emf);
            ServicioUsuario su = new ServicioUsuario(emf);
            HttpSession sesion = request.getSession();
            String msg = (String) sesion.getAttribute("msg");
            sesion.removeAttribute("msg"); // Para que no se muestre en futuras peticiones
            List<ExperienciaViaje> listadoExperiencias = sev.findExperienciaViajeEntities();
            List<Opinion> listadoOpiniones = so.findOpinionEntities();
            List <Usuario> listadoUsuarios = su.findUsuarioEntities();
            emf.close();
            request.setAttribute("msg", msg);
            request.setAttribute("listadoExperiencias",listadoExperiencias);
            request.setAttribute("listadoOpiniones", listadoOpiniones);
            request.setAttribute("listadoUsuarios", listadoUsuarios);
            getServletContext().getRequestDispatcher("/usuario/inicio.jsp").forward(request, response);
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
