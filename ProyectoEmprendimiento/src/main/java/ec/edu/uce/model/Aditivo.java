package ec.edu.uce.model;

import ec.edu.uce.enums.TipoAditivo;

public class Aditivo {
    private TipoAditivo tipoAditivo;
    private int cantidad;

    public Aditivo(TipoAditivo tipoAditivo, int cantidad) {
        this.tipoAditivo = tipoAditivo;
        this.cantidad = cantidad;
    }

    public TipoAditivo getTipoAditivo() {
        return tipoAditivo;
    }

    public void setTipoAditivo(TipoAditivo tipoAditivo) {
        this.tipoAditivo = tipoAditivo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

