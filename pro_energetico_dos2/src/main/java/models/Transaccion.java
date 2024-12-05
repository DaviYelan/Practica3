package models;

public class Transaccion {
    private String tipo;
    private String idProyecto;
    private String descripcion;
    private Integer idTransaccion; // Agrega el ID para la transacción

    public Transaccion(String tipo, String idProyecto, String descripcion) {
        this.tipo = tipo;
        this.idProyecto = idProyecto;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }
}
