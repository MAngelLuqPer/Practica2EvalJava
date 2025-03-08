/*
 * Controlador sencillo para añadir una opinion relacionada a una experiencia
 */
package controladores.usuario;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Opinion;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioOpinion;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorCrearOpinion", urlPatterns = {"/usuario/ControladorCrearOpinion"})
public class ControladorCrearOpinion extends HttpServlet {


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
                getServletContext().getRequestDispatcher("/usuario/crearOpinion.jsp").forward(request, response);
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
                String msg = "";
                long idExp = Long.parseLong(request.getParameter("idExp"));
                String comentario = request.getParameter("comentario");
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
                ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
                HttpSession sesion = request.getSession();
                //Se obtiene el usuario de la sesion, ya que el usuario logueado sera el autor del comentario
                Usuario usuSesion = (Usuario)sesion.getAttribute("usuario");
                ServicioOpinion so = new ServicioOpinion(emf);
                //Se obtiene la experiencia a la que va a pertenecer la opinion
                ExperienciaViaje expComentada = sev.findExperienciaViaje(idExp);
                Opinion op = new Opinion();
                op.setContenido(comentario);
                op.setExperiencia(expComentada);
                op.setUsuario(usuSesion);
                //Se crea la opinion con todos sus atributos (usuario, comentario y experiencia)
                so.create(op);
                msg = "Experiencia comentada correctamente";
                sesion.setAttribute("msg", msg);
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
