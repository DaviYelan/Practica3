package controller.Dao.servicies;

import controller.Dao.TransaccionDao;
import controller.tda.list.LinkedList;
import models.Transaccion;

public class TransaccionServices {
    private TransaccionDao transaccionDao;

    public TransaccionServices() {
        transaccionDao = new TransaccionDao();
    }

    public void registrarTransaccion(String tipo, String idProyecto, String descripcion) {
        Transaccion transaccion = new Transaccion(
            tipo,
            idProyecto,
            descripcion
        );
        try {
            transaccionDao.save(transaccion); // Guarda la transacción en el DAO
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de excepciones
        }
    }

    public LinkedList<Transaccion> listAllTransacciones() {
        return transaccionDao.getListAll();
    }
}
