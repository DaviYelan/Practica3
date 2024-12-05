package controller.Dao;


import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Inversionista;
public class InversionistaDao extends AdapterDao<Inversionista> {
    private Inversionista inversionista;
    private LinkedList<Inversionista> listAll;

    public InversionistaDao(){
        super(Inversionista.class);
    }

    public Inversionista getInversionista(){
        if(inversionista == null) {
            inversionista = new Inversionista();
        }
        return this.inversionista;
    }
    
    public void setInversionista(Inversionista inversionista) {
        this.inversionista = inversionista;
    }

    public LinkedList<Inversionista> getListAll(){
        if (this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        if (getInversionista().getIdpropiedad() == null) {
            throw new Exception("idpropiedad no está configurado para el inversionista");
        }
        Integer id = getListAll().getSize() + 1;
        getInversionista().setIdInversionista(id); // Asigna un nuevo id
        persist(getInversionista()); // Guarda el inversionista
        return true;
    }
    
    public Boolean update() throws Exception {
        this.merge(getInversionista(),getInversionista().getIdInversionista() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        // Comprobar si el Inversionista existe antes de intentar eliminarlo
        for (int i = 0; i < getListAll().getSize(); i++) {
            Inversionista inv = getListAll().get(i);
            if (inv.getIdInversionista().equals(id)) { // Usar equals() para comparación de Integer
                getListAll().delete(i);
                return true; // Eliminado correctamente
            }
        }
        throw new Exception("Inversionista no encontrado con ID: " + id); // No encontrado
    }

}
