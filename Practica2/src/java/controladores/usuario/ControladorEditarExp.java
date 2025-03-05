/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorEditarExp", urlPatterns = {"/usuario/ControladorEditarExp"})
public class ControladorEditarExp extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
            long id = Long.parseLong(request.getParameter("idExp"));
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            ExperienciaViaje expEdit = sev.findExperienciaViaje(id);
            emf.close();
            request.getSession().setAttribute("expEdit", expEdit);
            getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
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
            HttpSession sesion = request.getSession();
            String msg = "";
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("descripcion");
            String fechaInicioStr = request.getParameter("fechaInicio");
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            Date date = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            boolean publico = false;
            if (request.getParameter("publico") != null) {
                publico = true;
            }
            
            ExperienciaViaje expEdit =(ExperienciaViaje)sesion.getAttribute("expEdit");
            sesion.removeAttribute("expEdit");
            expEdit.setTitulo(titulo);
            expEdit.setPublico(publico);
            expEdit.setDescripcion(desc);
            expEdit.setFechaInicio(date);
        try {
            sev.edit(expEdit);
            msg="Experiencia editada correctamente";
        } catch (Exception ex) {
            msg="Error al editar la experiencia";
        }
        emf.close();
        sesion.setAttribute("msg", msg);
        response.sendRedirect("ControladorInicio");
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
