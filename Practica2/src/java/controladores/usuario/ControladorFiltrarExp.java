/*
 * Controlador encargado de filtrar la experiencias para devolver los datos correspondientes
 * ESTE CONTROLADOR SOLO LO RECIBIRA EL JS
 */
package controladores.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@WebServlet(name = "ControladorFiltrarExp", urlPatterns = {"/usuario/ControladorFiltrarExp"})
public class ControladorFiltrarExp extends HttpServlet {

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
        //Si no se ha escrito nada en el filtro, se establece una cadena vacia
        String filtro = request.getParameter("filtro");
        if (filtro == null) {
            filtro = "";
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        //Se obtiene todas las experiencias para filtrarlas mas adelante
        List<ExperienciaViaje> expFiltrar = sev.findExperienciaViajeEntities();
        System.out.println("Experiencias obtenidas: " + expFiltrar.size());
       //Eliminamos las mayusculas y espacios del texto a buscar
        filtro = filtro.toLowerCase().trim();
        //Listado vacio para añadir las experiencias filtradas
        List<ExperienciaViaje> expFiltrados = new ArrayList();
        for (ExperienciaViaje e: expFiltrar) {
            //Si el titulo o la descripcion en minusculas contiene algunas letras de lo que se desea buscar...
            if (e.getTitulo().toLowerCase().contains(filtro) || 
                    e.getDescripcion().toLowerCase().contains(filtro)) {
                //Se añade al nuevo listado de experiencias
                expFiltrados.add(e);
            }
        }
        System.out.println("Experiencias filtradas: "+expFiltrados.size());
        ServicioOpinion so = new ServicioOpinion(emf);
        ServicioUsuario su = new ServicioUsuario(emf);
        List<Opinion> listadoOpiniones = so.findOpinionEntities();
        List<Usuario> listadoUsuarios = su.findUsuarioEntities();
        emf.close();
        //Se envia a la vista los datos a mostrar junto a las experiencias filtradas
        request.setAttribute("listadoUsuarios", listadoUsuarios);
        request.setAttribute("listadoOpiniones", listadoOpiniones);
        request.setAttribute("listadoExperiencias", expFiltrados);
        getServletContext().getRequestDispatcher("/usuario/filtrarExp.jsp").forward(request, response);
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
