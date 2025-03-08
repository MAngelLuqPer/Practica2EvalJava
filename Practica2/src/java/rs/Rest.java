/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs;

import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author mangel
 * REST encargado de mostrar los datos de experiencias y actividades
 */
@Path("info")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Rest {
@GET
public Response hola() {
    //PRUEBA DE API
return Response.ok("{\"mensaje\": \"Hola, Mundo desde el API REST\"}", MediaType.APPLICATION_JSON).build();
    }



//API encargado de mostrar todas las actividades existentes.
@GET
@Path("act")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Response Actividades() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
    ServicioActividad sa = new ServicioActividad(emf);
    List<Actividad> actRest = sa.findActividadEntities();
return Response.ok(actRest, MediaType.APPLICATION_JSON).build();
    }




//NO FUNCIONA: Stack Overflow Error
//REST encargado de mostrar todas las experiencias
@GET
@Path("exp")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public Response experiencias() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
    ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
    List<ExperienciaViaje> expRest = sev.findExperienciaViajeEntities();
return Response.ok(expRest, MediaType.APPLICATION_JSON).build();
    }
}



