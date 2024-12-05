package com.ejemplo.rest;

import java.util.HashMap;
import controller.tda.list.LinkedList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import controller.Dao.servicies.InversionistaServices;
import models.Inversionista;


@Path("/inversionista")
public class InversionistaApi {

@Path("/listas/{acronimo}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getRelacion(@PathParam("acronimo") String acronimo) throws Exception {
    HashMap<String, Object> map = new HashMap<>();
    InversionistaServices is = new InversionistaServices();
    
    // Filtra los inversionistas relacionados con el acrónimo
    LinkedList<Inversionista> lista = is.listAll().filter(acronimo);

    if (lista.isEmpty()) {
        map.put("msg", "No hay inversionistas relacionados con el proyecto");
        map.put("data", new Object[]{});
    } else {
        map.put("msg", "Lista de inversionistas relacionados con el proyecto");
        map.put("data", lista.toArray());
    }
    
    return Response.ok(map).build();
}

    @Path("/asociar/{acronimo}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, String> map, @PathParam("acronimo") String acronimo) throws Exception {
        HashMap<String, String> res = new HashMap<>();
        try {
            InversionistaServices is = new InversionistaServices();
            Inversionista inversionista = is.getInversionista();
            inversionista.setNombre(map.get("nombre"));
            inversionista.setApellido(map.get("apellido"));
            inversionista.setDni(map.get("dni"));
            inversionista.setTelefono(map.get("telefono"));
            inversionista.setIdpropiedad(acronimo); // Establece el nombre del proyecto en lugar del id
            is.setInversionista(inversionista); // Guarda el objeto Inversionista en el servicio
            is.save(); // Llama al método save del servicio
            res.put("msg", "Inversionista asociado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            res.put("msg", "ERROR");
            res.put("data", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
