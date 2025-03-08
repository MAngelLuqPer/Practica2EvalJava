/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/ServletListener.java to edit this template
 */
package rs;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
/**
 * Web application lifecycle listener.
 *
 * @author mangel
 */
@ApplicationPath("/api")
public class ApplicationConfig extends ResourceConfig {
        public ApplicationConfig() {
        packages("rs"); // Escanea automáticamente todas las clases en este paquete
    }
}