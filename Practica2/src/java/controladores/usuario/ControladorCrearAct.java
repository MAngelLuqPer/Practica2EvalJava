/*
 * Controlador encargado de crear una actividad relacionada a una ExperienciaViaje
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
        //Redirige a la vista automaticamente
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
            //Recoge el id de la experiencia a la que va a pertenecer la actividad
            long idExp = Long.parseLong(request.getParameter("idExp"));
            //Recoge del formulario todo lo enviado para crear la actividad
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("descripcion");
            String fechaStr = request.getParameter("fecha");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = null;
            //Se intenta formatear la fecha
            try {
                fechaInicio = sdf.parse(fechaStr);
            } catch (ParseException ex) {
                System.out.println(ex);
            }
            //Se obtiene la ruta del directorio en donde se quiere guardar las fotos
            String path = getServletContext().getRealPath("/usuario/ImagenesExperiencia");
            System.out.println(" **** Path: " + path);
            List<String> imgs = new ArrayList<>();
            //Se obtiene una coleccion con todas las fotos recolectadas del formulario
            Collection<Part> partes = request.getParts();
            //Se iteran dicha informacion
            for (Part fichero : partes) {
            if ("imagenes".equals(fichero.getName()) && fichero.getSize() > 0) {
                /*Se establece el nombre de la imagen que se va a almacenar y su ruta
                el nombre del fichero empezara por la ID de la experiencia a la que pertenece la actividad
                separado por _ y seguido con el nombre del fichero enviado*/
                String nombreFichero = path + "/" + idExp + "_" + fichero.getSubmittedFileName();
                InputStream contenido = fichero.getInputStream();
                //Se crea el fichero de salida con el nombre y la ruta anteriormente compuesto
                FileOutputStream ficheroSalida = new FileOutputStream(nombreFichero);
                //Se escribe la informacion en el fichero que se le paso a FileOutputStream, anteriormente recogido en "contenido"
                byte[] buffer = new byte[8192];
                while (contenido.available() > 0) {
                    int bytesLeidos = contenido.read(buffer);
                    ficheroSalida.write(buffer, 0, bytesLeidos);
                }
                
                ficheroSalida.close();
                contenido.close();
                //Se añade la ruta de la foto en el array que contiene la actividad
                imgs.add(idExp + "_" + fichero.getSubmittedFileName());
            }
        }
            //Se obtiene el objeto ExperienciaViaje a la que esta asociada la experiencia
            ExperienciaViaje evAddAct = sev.findExperienciaViaje(idExp);
            Actividad nuevaAct = new Actividad();
            nuevaAct.setTitulo(titulo);
            nuevaAct.setDescripcion(desc);
            nuevaAct.setFecha(fechaInicio);
            nuevaAct.setImagenes(imgs);
            //Una vez se crea el objeto Actividad con todos sus atributos, se añade a la DB
            sa.create(nuevaAct);
            //Se saca la lista de actividades de la experiencia y se le añade la nueva actividad creada
            List<Actividad> expActEdit = evAddAct.getActividades();
            expActEdit.add(nuevaAct);
        try {
            //Se intenta modificar la experiencia para añadir la nueva actividad asociada
            sev.edit(evAddAct);
        } catch (Exception ex) {
            Logger.getLogger(ControladorCrearAct.class.getName()).log(Level.SEVERE, null, ex);
        }
         emf.close();
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
