/*
 * Controlador encargado de la gran parte de la pestaña de inicio y sus experiencias con actividades.
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
        //Se obtiene de la sesion la accion a cometer
            String accion = request.getParameter("accion");
            HttpSession sesion = request.getSession();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            String msg = "";
            //Si no se ha cometido ninguna accion...
        if (accion == null) {
            getServletContext().getRequestDispatcher("/usuario/crearExperiencia.jsp").forward(request, response);
        } else if (accion.equals("eliminarExp")) { //Si se ha pulsado en borrar...
            long id = Long.parseLong(request.getParameter("id"));
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            //Se obtiene la experienciaviaje que se va a borrar
            ExperienciaViaje evDelOpinion = sev.findExperienciaViaje(id);
            ServicioOpinion so = new ServicioOpinion(emf);
            List<Actividad> actividadesDel = evDelOpinion.getActividades();
            //Se comprueba que no tiene actividades asignadas
            if (!actividadesDel.isEmpty()) {
                msg = "No se ha podido borrar la Experiencia. Pruebe a borrar antes las actividades.";
            } else { //Si no tiene actividades...
                try {
                    //Se borran todas las opiniones relacionadas con la experiencia
                    so.eliminarPorExperiencia(evDelOpinion);
                } catch (NonexistentEntityException ex) {
                    System.out.println("No se encontraron opiniones para la experiencia con id " + id);
                }
                try {
                    //Se elimina la experiencia
                    sev.destroy(id);
                    msg = "Experiencia borrada correctamente";
                } catch (Exception ex) {
                    msg = "No se ha podido borrar la Experiencia. Pruebe a borrar antes las actividades.";
                }
            }
            //Una vez borrado, se vuelve a la vista inicio
            sesion.setAttribute("msg", msg);    
            response.sendRedirect("ControladorInicio");
            
        } else if (accion.equals("eliminarAct")) {//Si se la pulsado en borrar actividad....
            //Se obtiene el id de la actividad y de la experiencia que tiene relacionada
            long id = Long.parseLong(request.getParameter("id"));
            long idExp = Long.parseLong(request.getParameter("idExp"));
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            Actividad actDel = sa.findActividad(id);
            ExperienciaViaje expViajeDelAct = sev.findExperienciaViaje(idExp);
            //De la experiencia asociada a la actividad, se obtiene las actividades
            List<Actividad> actividadesExp = expViajeDelAct.getActividades();
            //Se borra del listado de actividades de la experiencia la actividad a borrar.
            actividadesExp.remove(actDel);
            //Se establece el nuevo listado de actividades a la experiencia
            expViajeDelAct.setActividades(actividadesExp);
                try {
                    //Se edita la experiencia
                    sev.edit(expViajeDelAct);
                } catch (Exception ex) {
                    msg = "Error al borrar la actividad de la experiencia";
                }
            
                try {
                    //Se elimina la actividad
                    sa.destroy(id);
                    msg = "La actividad se borro correctamente";
                } catch (Exception ex) {
                    msg = "No se ha podido borrar la actividad";
                }
                sesion.setAttribute("msg",msg);
                response.sendRedirect("ControladorInicio");
        } else if (accion.equals("editarAct")) { //Si se pulso en editar...
            long id = Long.parseLong(request.getParameter("id"));
            //Se establece el id en la sesion y se redirige a otro controlador.
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
    
    //Encargado de crear una nueva experiencia
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
            String msg = "";
            //Se recoge los parametros del formulario
            String titulo = request.getParameter("titulo");
            String desc = request.getParameter("descripcion");
            String fechaInicioStr = request.getParameter("fechaInicio");
            //Se pasa de string a localdate
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            HttpSession sesion = request.getSession();
            //El creador de la experiencia sera el usuario que este logueado
            Usuario usuarioAutor =(Usuario) sesion.getAttribute("usuario");
            Date date = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            //Misma logica seguida en editarExp, si es null, no se ha marcado la casilla de publico...
            boolean privacidad = false;
            if (request.getParameter("privacidad")!=null) {
                privacidad = true;
            }
            //Se crea y asigna los atributos a la nueva experiencia
            ExperienciaViaje nuevoExpViaje = new ExperienciaViaje();
            nuevoExpViaje.setTitulo(titulo);
            nuevoExpViaje.setDescripcion(desc);
            nuevoExpViaje.setFechaInicio(date);
            nuevoExpViaje.setPublico(privacidad);
            nuevoExpViaje.setUsuario(usuarioAutor);
            try {
                //Se intenta crear
                sev.create(nuevoExpViaje);
                msg = "Experiencia creada correctamente";
            } catch (Exception e) {
                msg = "Error al crear la experiencia" + e;
            }
            emf.close();
            //Se reenvia al crear experiencia por si se quiere crear otro mas.
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
