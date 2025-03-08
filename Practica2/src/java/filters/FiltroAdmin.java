/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.Usuario;

/**
 *
 * @author mangel
 * Filtro sencillo encargado de las peticiones a los controladores /admin
 */
@WebFilter(filterName = "FiltroAdmin", urlPatterns = {"/admin/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.INCLUDE})
public class FiltroAdmin implements Filter {
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
            
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession sesion = req.getSession();
            Usuario usuario = (Usuario)sesion.getAttribute("usuario");
            String rol ="";
            //Si el usuario de la sesion es null, se le redirigira al login
            if (usuario == null) {
                res.sendRedirect(req.getServletContext().getContextPath()+"/ControladorLogin");
                return;
            } else { //Si existe el usuario en la sesion...
                rol = usuario.getTipo();
                //Se obtiene el tipo de usuario que es, y si no es admin, se le redirige a la pagina principal
                if (!rol.equals("admin")) {
                    res.sendRedirect(req.getServletContext().getContextPath()+"/usuario/ControladorInicio");
                    return;
                }
            }
            
            chain.doFilter(request, response);
        
    }
        
        // If there was a problem, we want to rethrow it if it is

    /**
     * Return the filter configuration object for this filter.
     */

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
    }
    
}