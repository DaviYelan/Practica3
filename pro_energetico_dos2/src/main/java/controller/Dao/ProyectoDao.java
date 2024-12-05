package controller.Dao;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import models.Proyecto;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private LinkedList<Proyecto> listAll;

    public ProyectoDao(){
        super(Proyecto.class);
    }

    public Proyecto getProyecto() {
        if(proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public LinkedList<Proyecto> getListAll() {
        if(this.listAll == null) {
            this.listAll = listAll();
        }
        return this.listAll; 
    }

    public Boolean save() throws Exception {
        Integer id =  getListAll().getSize()+1;
        getProyecto().setIdProyecto(id);
        persist(getProyecto());
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getIdProyecto() - 1);
        this.listAll = listAll();
        return true;
    }

    public Boolean delete(Integer id) throws Exception {
        for(int i = 0; i < getListAll().getSize(); i++) {
            Proyecto pro = getListAll().get(i);
            if(pro.getIdProyecto().equals(id)) {
                getListAll().delete(i);
                return true;
            }
        }
        throw new Exception("Proyecto no encontrado con ID: " + id);
    }

    
    
}
