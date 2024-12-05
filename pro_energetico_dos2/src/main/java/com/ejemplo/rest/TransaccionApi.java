package com.ejemplo.rest;
import controller.Dao.servicies.TransaccionServices;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/transaccion")
public class TransaccionApi {

    private final TransaccionServices transaccionServices = new TransaccionServices();

    // Endpoint para listar todas las transacciones
    @GET
    @Path("/lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransacciones() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("msg", "Lista de transacciones");
            map.put("data", transaccionServices.listAllTransacciones().toArray());
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "Error al obtener la lista de transacciones");
            map.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}
