package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Transaccion;

public class TransaccionDao extends AdapterDao<Transaccion> {
    private LinkedList<Transaccion> listAll;

    public TransaccionDao() {
        super(Transaccion.class);
    }

    public LinkedList<Transaccion> getListAll() {
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save(Transaccion transaccion) throws Exception {
        // Genera el ID y lo asigna a la transacción antes de guardarla
        Integer id = getListAll().getSize() + 1; // Suponiendo que este ID sea secuencial
        transaccion.setIdTransaccion(id);
        persist(transaccion); // Persiste la transacción
        return true;
    }
}
