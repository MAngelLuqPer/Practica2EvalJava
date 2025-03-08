/*
 * Controlador encargado de iniciar sesion y guardar el usuario en la sesion
 */
package controladores;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author mangel
 */
@WebServlet(name = "ControladorLogin", urlPatterns = {"/ControladorLogin"})
public class ControladorLogin extends HttpServlet {
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
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
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
         String email = request.getParameter("email");
         String pwd = request.getParameter("pwd");
         String error = "";
         //Obliga al usuario a rellenar todos los campos
         if (email == null || pwd == null || email.isEmpty() || pwd.isEmpty()) {
             error = "Contraseña y email obligatorios ";
         }
         else {
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
             ServicioUsuario su = new ServicioUsuario(emf);
             //Se comprueba si existe el usuario con la contraseña con el que se intenta iniciar sesion
             Usuario usu = su.validarUsuario(email, pwd);
             
             emf.close();
             if (usu != null) { //Si el usuario introducido existe...
                if (!usu.isActivo()) { //Si no esta activo, mostrara un error
                 error = "El usuario debe de ser activado por un administrador antes. Contacte con el departamento de administacion";
                } else { //Si esta activo...
                 HttpSession sesion = request.getSession();
                 sesion.setAttribute("usuario",usu);
                 //Uso de la ruta modificada del @WebServlet
                 response.sendRedirect("usuario/ControladorInicio");
                 return;
                 }
             } else {
                 error = "El usuario introducido no existe";
             }
         }
         //En caso de fallar el login, se mostrara el error
         request.setAttribute("error",error);
         getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
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
