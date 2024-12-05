package controller.Dao.servicies;

import controller.Dao.ProyectoDao;
import controller.tda.list.LinkedList;
import models.Proyecto;
public class ProyectoServices {
    private ProyectoDao obj;
    public ProyectoServices() {
        obj = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList<Proyecto> listAll(){
        return obj.getListAll();
    }

    public Proyecto getProyecto(){
        return obj.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public Proyecto get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id);
    }

    // Cosas Practica_3///////////////////////////////////////////////////////////////////////////////////////

    // Nuevos métodos de búsqueda
    public LinkedList<Proyecto> buscarProyectos(Object criterio, String atributo, String metodoBusqueda)
            throws Exception {
        switch (metodoBusqueda) {
            case "binario":
                return obj.getListAll().binarySearch(criterio, atributo);
            case "lineal":
                return obj.getListAll().linearSearch(criterio, atributo);
            default:
                throw new Exception("Método de búsqueda no válido");
        }
    }

    // Métodos de ordenamiento
    public void ordenarProyectos(String atributo, String algoritmo, boolean ascendente) throws Exception {
        LinkedList<Proyecto> lista = obj.getListAll();
        switch (algoritmo) {
            case "quicksort":
                lista.quickSort(atributo, ascendente);
                break;
            case "mergesort":
                lista.mergeSort(atributo, ascendente);
                break;
            case "shellsort":
                lista.shellSort(atributo, ascendente);
                break;
            default:
                throw new Exception("Algoritmo de ordenamiento no válido");
        }
    }

}

