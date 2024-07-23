package ec.edu.uce.enums;

public enum TipoAditivo {
    OCTANE_BOOSTER("Octane_Booster"),
    INJECTOR_CLEANER("Injector_Cleaner"),
    DIESEL_INJECTOR("Diesel_Injector");

    private String nombre;

    TipoAditivo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
