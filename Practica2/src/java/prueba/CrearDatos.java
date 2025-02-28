/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Opinion;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioOpinion;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author jose
 */
@WebServlet(name = "CrearDatos", urlPatterns = {"/CrearDatos"})
public class CrearDatos extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioUsuario su = new ServicioUsuario(emf);
        ServicioExperienciaViaje se = new ServicioExperienciaViaje(emf);
        ServicioActividad sa = new ServicioActividad(emf);
        ServicioOpinion so = new ServicioOpinion(emf);
        Usuario usuario = new Usuario();
        usuario.setNombre("Pepe");
        usuario.setApellidos("Pérez");
        usuario.setEmail("pep@iescamas.es");
        usuario.setPassword("1234");
        usuario.setTipo("nornal");
        usuario.setActivo(true);
        su.create(usuario);
        ExperienciaViaje e1 = new ExperienciaViaje();
        e1.setTitulo("Vacaciones de verano 2024");
        e1.setDescripcion("Pasamos unos días en los Pirineos");
        e1.setFechaInicio(new Date());
        e1.setUsuario(usuario);
        Actividad a1 = new Actividad();
        a1.setTitulo("Sendero del valle de Ordesa");
        a1.setFecha(new Date());
        a1.setDescripcion("Subimos por el valle de Ordesa hasta la cascada de La Cola de Caballo");
        a1.getImagenes().add("foto1");
        e1.getActividades().add(a1);
        sa.create(a1);
        se.create(e1);
        Opinion o1 = new Opinion();
        o1.setUsuario(usuario);
        o1.setExperiencia(e1);
        o1.setContenido("Es una ruta espectacular");
        so.create(o1);
        emf.close();
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CrearDatos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Se han creado los datos de prueba</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
