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
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorExperiencias", urlPatterns = {"/usuario/ControladorExperiencias"})
public class ControladorExperiencias extends HttpServlet {

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
        getServletContext().getRequestDispatcher("/usuario/crearExperiencia.jsp").forward(request, response);
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
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            String msg = "";
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("descripcion");
            String fechaInicioStr = request.getParameter("fechaInicio");
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            HttpSession sesion = request.getSession();
            Usuario usuarioAutor =(Usuario) sesion.getAttribute("usuario");
            Date date = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            boolean privacidad = false;
            if (request.getParameter("privacidad")!=null) {
                privacidad = true;
            }
            ExperienciaViaje nuevoExpViaje = new ExperienciaViaje();
            nuevoExpViaje.setTitulo(titulo);
            nuevoExpViaje.setDescripcion(desc);
            nuevoExpViaje.setFechaInicio(date);
            nuevoExpViaje.setPublico(privacidad);
            nuevoExpViaje.setUsuario(usuarioAutor);
            try {
                sev.create(nuevoExpViaje);
                msg = "Experiencia creada correctamente"+usuarioAutor.getNombre();
            } catch (Exception e) {
                msg = "Error al crear la experiencia" + e;
            }
            request.setAttribute("msg", msg);
            getServletContext().getRequestDispatcher("/usuario/crearExperiencia.jsp").forward(request, response);
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
