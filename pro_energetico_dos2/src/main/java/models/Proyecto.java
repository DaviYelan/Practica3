package models;


public class Proyecto {
    // Atributos de la clase Proyecto
    private Integer idProyecto; // Identificador único del proyecto
    private String nombre; // Nombre del proyecto
    private Float inversion; // Monto de la inversión en el proyecto
    private Integer tiempodevida; // Tiempo de vida del proyecto en años
    private String fechaInicio; // Fecha de inicio del proyecto
    private String fechaFin; // Fecha de finalización del proyecto
    private Float electicidadGeneradapordia; // Cantidad de electricidad generada por día\
    private String acronimo; // Acrónimo del proyecto
    private Float costototal; // Costo total del proyecto

    
    // Constructor con parámetros para inicializar todos los atributos
    public Proyecto(Integer idProyecto, String nombre, Float inversion, Integer tiempodevida, String fechaInicio, String fechaFin, Float electicidadGeneradapordia,String acronimo, Float costototal) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.inversion = inversion;
        this.tiempodevida = tiempodevida;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.electicidadGeneradapordia = electicidadGeneradapordia;
        this.acronimo = acronimo;
        this.costototal = costototal;
    }

    // Métodos getter y setter para cada atributo

    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getInversion() {
        return inversion;
    }

    public void setInversion(Float inversion) {
        this.inversion = inversion;
    }

    public Integer getTiempodevida() {
        return tiempodevida;
    }

    public void setTiempodevida(Integer tiempodevida) {
        this.tiempodevida = tiempodevida;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Float getElecticidadGeneradapordia() {
        return electicidadGeneradapordia;
    }

    public void setElecticidadGeneradapordia(Float electicidadGeneradapordia) {
        this.electicidadGeneradapordia = electicidadGeneradapordia;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public Float getCostototal() {
        return costototal;
    }

    public void setCostototal(Float costototal) {
        this.costototal = costototal;
    }




    // Constructor por defecto
    public Proyecto() {
    }

    
}
