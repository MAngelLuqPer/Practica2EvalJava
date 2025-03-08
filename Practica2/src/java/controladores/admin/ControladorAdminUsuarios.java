/*
 * Controlador encargado de toda la seccion de administrar usuario (editar, eliminar y vista) 
*/
package controladores.admin;

import Utilidades.Utilidades;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Email;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioUsuario;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorAdminUsuarios", urlPatterns = {"/admin/ControladorAdminUsuarios"})
public class ControladorAdminUsuarios extends HttpServlet {

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
    
    //Esto es un controlador monolito, es decir, se encarga de todas las opciones de editar usuario
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
       ServicioUsuario su = new ServicioUsuario(emf);
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
       if (request.getParameter("accion") == null) { //Si no se ha pulsado en editar...
       List<Usuario> listaUsuarios = su.findUsuarioEntities();
       List<ExperienciaViaje> expUsuarios = sev.findExperienciaViajeEntities();
       //Se va a la vista con los usuarios y las experiencias para mostrarlo en las tablas
       request.setAttribute("expUsuarios",expUsuarios);
       request.setAttribute("listaUsuarios", listaUsuarios);
       getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
       } else if (request.getParameter("accion").equals("editar")){ // Si se ha pulsado en editar...
           //Se recoge el id de la URL, se busca el usuario con dicha id y se envia a la vista de edicion
           long id = Long.parseLong(request.getParameter("id"));
           Usuario usuEditar = su.findUsuario(id);
           request.setAttribute("usuEditar", usuEditar);
           request.getSession().setAttribute("usuEditar", usuEditar);
           getServletContext().getRequestDispatcher("/admin/editarUsuario.jsp").forward(request, response);
       } else { //Si no es ni editar ni es null, significa que se ha pulsado en eliminar...
           long id = Long.parseLong(request.getParameter("id"));
           String msg = "";
           try {
               //Se intenta destruir el usuario con el id recogido por la URL
               su.destroy(id);
               msg = "El usuario se ha borrado correctamente.";
           } catch (NonexistentEntityException ex) {
               Logger.getLogger(ControladorAdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
               msg = "Error al borrar el usuario";
           }
           List<Usuario> listaUsuarios = su.findUsuarioEntities();
           List<ExperienciaViaje> expUsuarios = sev.findExperienciaViajeEntities();
           emf.close();
           /*Una vez borrado, se recoge otra vez la lista de los usuarios y las experiencias para volver a mostrar
             el listado de todos los usuarios*/
           request.setAttribute("expUsuarios",expUsuarios);
           request.setAttribute("listaUsuarios", listaUsuarios);
           request.setAttribute("msg",msg);
           getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
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
        //Controla las acciones post del formulario para editar usuarios
        //Pilla el usuario asignado en la sesion a la hora de pulsar en editar.
            Usuario usuEditado = (Usuario) request.getSession().getAttribute("usuEditar");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioUsuario su = new ServicioUsuario(emf);
            String msg = "";
            if (usuEditado != null) {
                //Se recoge los campos del form
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String pwd = request.getParameter("pwd");
                boolean activo = false;
                if (request.getParameter("activo") != null) {
                    activo = true;
                }
                boolean estadoAnterior = usuEditado.isActivo();
                //Se edita el usuario recogido anteriormente
                usuEditado.setNombre(nombre);
                usuEditado.setApellidos(apellidos);
                usuEditado.setPassword(pwd);
                usuEditado.setActivo(activo);
                //LOGICA PARA ENVIAR EMAILS
                    if (!estadoAnterior && activo) {
                     /*Se crea un nuevo objeto tipo Email, se establece un titulo de correo fijo
                        el contenido del email enviado contendra un texto fijo junto al nombre del usuario que se vaya a activar
                        la direccion de correo (from) ponemos el correo que queramos que sea el emisor del e-mail
                        Luego con las utilidades del e-mail, hacemos un e.enviarEmail, que contendra el e-mail del from 
                        y una contraseña generada por Google especifica para aplicaciones*/
                     Email email = new Email();
                     email.setTo(usuEditado.getEmail());
                     email.setSubject("Cuenta activada correctamente");
                     email.setText("Su cuenta de JavaTraveling se ha activado correctamente. ¡Disfrute de su experiencia en esta red social "+usuEditado.getNombre()+ "!");
                     email.setFrom("luque.perez.miguel.angel@iescamas.es");
                     Utilidades u = new Utilidades();
                     System.out.println("El email se ha enviado correctamente");
                    try {
                     u.enviarEmail(email, "bdty fsds qfmz oyze");
                    } catch (Throwable e) {
                        System.out.println("\"Error al enviar e-mail: <br>\" + e.getClass().getName() + \":\" + \n" +
"                                e.getMessage()");
                    }
                   }
                
                try {
                    //Se intenta editar el usuario...
                    su.edit(usuEditado);
                    msg = "Usuario editado correctamente.";
                } catch (Exception ex) {
                    msg = "Error al editar el usuario: "+ex;
                }
                request.getSession().removeAttribute("usuEditar");
            } else {
                msg = "No hay usuario que editar";
            }
            List<Usuario> listaUsuarios = su.findUsuarioEntities();
            emf.close();
            //Cuando se termien toda la logica de edicion, se volvera a la vista con la tabla de todos los usuarios
            request.setAttribute("listaUsuarios", listaUsuarios);
            request.setAttribute("msg", msg);
            getServletContext().getRequestDispatcher("/admin/adminUsuarios.jsp").forward(request, response);
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
