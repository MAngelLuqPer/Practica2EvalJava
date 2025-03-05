/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.usuario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioOpinion;
import modelo.servicio.exceptions.NonexistentEntityException;

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
            String accion = request.getParameter("accion");
            HttpSession sesion = request.getSession();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            String msg = "";
        if (accion == null) {
            getServletContext().getRequestDispatcher("/usuario/crearExperiencia.jsp").forward(request, response);
        } else if (accion.equals("eliminarExp")) {
            long id = Long.parseLong(request.getParameter("id"));
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            ExperienciaViaje evDelOpinion = sev.findExperienciaViaje(id);
            ServicioOpinion so = new ServicioOpinion(emf);
            ExperienciaViaje expDel = sev.findExperienciaViaje(id);
            List<Actividad> actividadesDel = expDel.getActividades();
            if (!actividadesDel.isEmpty()) {
                msg = "No se ha podido borrar la Experiencia. Pruebe a borrar antes las actividades.";
            } else {
                try {
                    so.eliminarPorExperiencia(evDelOpinion);
                } catch (NonexistentEntityException ex) {
                    System.out.println("No se encontraron opiniones para la experiencia con id " + id);
                }
                try {
                    sev.destroy(id);
                    msg = "Experiencia borrada correctamente";
                } catch (Exception ex) {
                    msg = "No se ha podido borrar la Experiencia. Pruebe a borrar antes las actividades.";
                }
            }
            sesion.setAttribute("msg", msg);    
            response.sendRedirect("ControladorInicio");
            
        } else if (accion.equals("eliminarAct")) {

            long id = Long.parseLong(request.getParameter("id"));
            long idExp = Long.parseLong(request.getParameter("idExp"));
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            Actividad actDel = sa.findActividad(id);
            ExperienciaViaje expViajeDelAct = sev.findExperienciaViaje(idExp);
            List<Actividad> actividadesExp = expViajeDelAct.getActividades();
            actividadesExp.remove(actDel);
            expViajeDelAct.setActividades(actividadesExp);
                try {
                    sev.edit(expViajeDelAct);
                } catch (Exception ex) {
                    msg = "Error al borrar la actividad de la experiencia";
                }
            
                try {
                    sa.destroy(id);
                    msg = "La actividad se borro correctamente";
                } catch (Exception ex) {
                    msg = "No se ha podido borrar la actividad";
                }
                sesion.setAttribute("msg",msg);
                response.sendRedirect("ControladorInicio");
        } else if (accion.equals("editarAct")) {
            long id = Long.parseLong(request.getParameter("id"));
            sesion.setAttribute("id", id);
            response.sendRedirect("ControladorEditarAct");
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
