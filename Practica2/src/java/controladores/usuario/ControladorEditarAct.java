/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.usuario;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.Actividad;
import modelo.servicio.ServicioActividad;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorEditarAct", urlPatterns = {"/usuario/ControladorEditarAct"})
public class ControladorEditarAct extends HttpServlet {

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
            HttpSession sesion = request.getSession();
            long id = (long)sesion.getAttribute("id") ;
            sesion.removeAttribute("id");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            Actividad actEditar = sa.findActividad(id);
            request.setAttribute("actEditar", actEditar);
            request.getSession().setAttribute("actEditar", actEditar);
            getServletContext().getRequestDispatcher("/usuario/editarAct.jsp").forward(request, response);
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
            Actividad actEditar = (Actividad)request.getSession().getAttribute("actEditar");
            String msg = "";
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("descripcion");
            String fechaStr = request.getParameter("fecha");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = null;
        try {
            fechaInicio = sdf.parse(fechaStr);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
            
            actEditar.setTitulo(titulo);
            actEditar.setDescripcion(desc);
            actEditar.setFecha(fechaInicio);
        try {
            sa.edit(actEditar);
            msg="Actividad editada correctamente";
        } catch (Exception ex) {
            msg ="Error al editar la actividad";
        }
        request.getSession().removeAttribute("actEditar");
        request.getSession().setAttribute("msg", msg);
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
