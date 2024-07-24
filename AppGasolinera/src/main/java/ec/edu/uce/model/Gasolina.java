package ec.edu.uce.model;

import ec.edu.uce.util.TipoGasolina;

public class Gasolina {

    private TipoGasolina tipoGasolina;
    private float precio;
    private float galones;

    public Gasolina(TipoGasolina tipoGasolina, float precio, float galones) {
        this.tipoGasolina = tipoGasolina;
        this.precio = precio;
        this.galones = galones;
    }

    public TipoGasolina getTipoGasolina() {
        return tipoGasolina;
    }

    public void setTipoGasolina(TipoGasolina tipoGasolina) {
        this.tipoGasolina = tipoGasolina;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getGalones() {
        return galones;
    }

    public void setGalones(float galones) {
        this.galones = galones;
    }

    @Override
    public String toString() {
        return tipoGasolina + ":" + precio + ":" + galones;
    }
}
