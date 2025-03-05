/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 */
@WebFilter(filterName = "FiltroUsuario", urlPatterns = {"/usuario/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.INCLUDE})
public class FiltroUsuario implements Filter {
    
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
            
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession sesion = req.getSession();
            Usuario usuario = (Usuario)sesion.getAttribute("usuario");
            String rol = usuario.getTipo();
            if (usuario == null) {
                res.sendRedirect(req.getServletContext().getContextPath()+"/ControladorLogin");
                return;
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