package com.ejemplo.rest;

import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import java.util.logging.Level;


import controller.Dao.servicies.InversionistaServices;;

@Path("/inversion")  // Cambiado para que coincida con la URL que mostramos
public class MyResource {
    
    private static final Logger logger = Logger.getLogger(MyResource.class.getName());
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap<String, Object> mapa = new HashMap<>();
        InversionistaServices is = new InversionistaServices();
        String aux = "";
        try {
            is.getInversionista().setNombre("Cheguevara");
            is.save();

            aux = "lista vacias: " + is.listAll().isEmpty();
            logger.info("Guardado exitoso. Lista: " + aux);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al guardar: " + e.getMessage(), e);
            e.printStackTrace();
            mapa.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        mapa.put("msg", "Proyecto guardado");
        mapa.put("data", "test " + aux);

        return Response.ok(mapa).build();
    }


}
