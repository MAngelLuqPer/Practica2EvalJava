/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorEstadisticas", urlPatterns = {"/admin/ControladorEstadisticas"})
public class ControladorEstadisticas extends HttpServlet {

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
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioUsuario su = new ServicioUsuario(emf);
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            ServicioActividad sa = new ServicioActividad(emf);
            List<Usuario> listaUsuarios = su.findUsuarioEntities();
            
            
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFinal");
            List<ExperienciaViaje> listadoExpFiltrado;
            if (fechaInicioStr != null && fechaFinStr != null) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaInicio = sdf.parse(fechaInicioStr);
                    Date fechaFin = sdf.parse(fechaFinStr);
                    if (fechaInicio.after(fechaFin)) {
                        request.setAttribute("error", "La fecha de inicio no puede ser mayor que la fecha de fin.");
                        listadoExpFiltrado = sev.findExperienciaViajeEntities();
                    } else {
                        listadoExpFiltrado = sev.findExperienciasByFecha(fechaInicio, fechaFin);
                        System.out.println("Filtrados: " + listadoExpFiltrado.size());
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Error al procesar las fechas. Verifica el formato.");
                    listadoExpFiltrado = sev.findExperienciaViajeEntities();
                }
            } else {
                listadoExpFiltrado = sev.findExperienciaViajeEntities();
            }

            
            
            request.setAttribute("listadoExp", listadoExpFiltrado);
            request.setAttribute("listaUsuarios", listaUsuarios);
            getServletContext().getRequestDispatcher("/admin/estadisticas.jsp").forward(request, response);
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
