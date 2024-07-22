package ec.edu.uce.model;

public class Gasolinera {
    private String tipoGasolina;
    private float precio;
    private float galones;

    public Gasolinera(String tipoGasolina, float precio, float galones) {
        this.tipoGasolina = tipoGasolina;
        this.precio = precio;
        this.galones = galones;
    }

    public String getTipoGasolina() {
        return tipoGasolina;
    }

    public void setTipoGasolina(String tipoGasolina) {
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
}
