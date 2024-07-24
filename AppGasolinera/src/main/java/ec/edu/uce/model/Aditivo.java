package ec.edu.uce.model;


import ec.edu.uce.util.TipoAditivo;

public class Aditivo {

    private TipoAditivo tipoAditivo;
    private float precio;
    private int unidades;

    public Aditivo(TipoAditivo tipoAditivo, float precio, int unidades) {
        this.tipoAditivo = tipoAditivo;
        this.precio = precio;
        this.unidades = unidades;
    }

    public TipoAditivo getTipoAditivo() {
        return tipoAditivo;
    }

    public void setTipoAditivo(TipoAditivo tipoAditivo) {
        this.tipoAditivo = tipoAditivo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    @Override
    public String toString() {
        return tipoAditivo + ":" + precio + ":" + unidades;
    }
}
