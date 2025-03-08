/*
 *Controlador encargado de enviar la informacion al jsp de graficas y estadisticas, pudiendo filtrar por fechas si se desea
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
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
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
            List<Usuario> listaUsuarios = su.findUsuarioEntities();
            String msg = "";
            String error = "";
            //Se recoge del pequeño formulario para filtrar entre fechas, las fechas correspondientes
            String fechaInicioStr = request.getParameter("fechaInicio");
            String fechaFinStr = request.getParameter("fechaFinal");
            List<ExperienciaViaje> listadoExpFiltrado;
            //Si se ha enviado una fecha a traves del form...
            if (fechaInicioStr != null && fechaFinStr != null) {
                try {
                    //Se intenta formatear las fechas recogidas como String anteriormente
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaInicio = sdf.parse(fechaInicioStr);
                    Date fechaFin = sdf.parse(fechaFinStr);
                    //Si la fecha de inicio es antes que la fecha final...
                    if (fechaInicio.after(fechaFin)) {
                        error = "La fecha de inicio no puede ser mayor que la fecha de fin.";
                        request.setAttribute("error", error);
                        listadoExpFiltrado = sev.findExperienciaViajeEntities();
                    } else { //Si las fechas son coherentes...
                        msg = "Fechas filtradas entre "+fechaInicioStr+" y "+fechaFinStr;
                        listadoExpFiltrado = sev.findExperienciasByFecha(fechaInicio, fechaFin);
                        System.out.println("Filtrados: " + listadoExpFiltrado.size());
                    }
                } catch (Exception e) {
                    error = "Error al procesar las fechas. Verifica el formato.";
                    request.setAttribute("error", error);
                    listadoExpFiltrado = sev.findExperienciaViajeEntities();
                }
            } else {
                //Si no se ha enviado a traves del formulario las fechas, se devolvera todas las experiencias sin filtrar
                listadoExpFiltrado = sev.findExperienciaViajeEntities();
            }

            emf.close();
            //Cuando termine toda la logica de filtrado, se vuelve a la vista con todas las graficas
            request.setAttribute ("msg",msg);
            request.setAttribute("error",error);
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
