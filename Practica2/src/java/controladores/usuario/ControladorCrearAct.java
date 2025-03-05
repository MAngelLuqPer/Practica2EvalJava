/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores.usuario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorCrearAct", urlPatterns = {"/usuario/ControladorCrearAct"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class ControladorCrearAct extends HttpServlet {

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
//        long idExp = Long.parseLong(request.getParameter("idExp"));
//        request.setAttribute("idExp", idExp);
        getServletContext().getRequestDispatcher("/usuario/crearActividad.jsp").forward(request, response);
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
            long idExp = Long.parseLong(request.getParameter("idExp"));
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("descripcion");
            String fechaStr = request.getParameter("fecha");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = null;
            try {
                fechaInicio = sdf.parse(fechaStr);
            } catch (ParseException ex) {
                System.out.println(ex);
            }
            String path = getServletContext().getRealPath("/usuario/ImagenesExperiencia");
            System.out.println(" **** Path: " + path);
            List<String> imgs = new ArrayList<>();
            Collection<Part> partes = request.getParts();
            for (Part fichero : partes) {
            if ("imagenes".equals(fichero.getName()) && fichero.getSize() > 0) {
                String nombreFichero = path + "/" + idExp + "_" + fichero.getSubmittedFileName();
                InputStream contenido = fichero.getInputStream();
                FileOutputStream ficheroSalida = new FileOutputStream(nombreFichero);
                
                byte[] buffer = new byte[8192];
                while (contenido.available() > 0) {
                    int bytesLeidos = contenido.read(buffer);
                    ficheroSalida.write(buffer, 0, bytesLeidos);
                }
                
                ficheroSalida.close();
                contenido.close();
                
                imgs.add(idExp + "_" + fichero.getSubmittedFileName());
            }
        }

            ExperienciaViaje evAddAct = sev.findExperienciaViaje(idExp);
            Actividad nuevaAct = new Actividad();
            nuevaAct.setTitulo(titulo);
            nuevaAct.setDescripcion(desc);
            nuevaAct.setFecha(fechaInicio);
            nuevaAct.setImagenes(imgs);
            sa.create(nuevaAct);
            List<Actividad> expActEdit = evAddAct.getActividades();
            expActEdit.add(nuevaAct);
        try {
            sev.edit(evAddAct);
        } catch (Exception ex) {
            Logger.getLogger(ControladorCrearAct.class.getName()).log(Level.SEVERE, null, ex);
        }
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
