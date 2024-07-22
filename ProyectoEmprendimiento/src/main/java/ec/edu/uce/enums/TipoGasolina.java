package ec.edu.uce.enums;

public enum TipoGasolina {
    SUPER("Super"),
    EXTRA("Extra"),
    DIESEL("Diesel");



    private String nombre;

    TipoGasolina(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

