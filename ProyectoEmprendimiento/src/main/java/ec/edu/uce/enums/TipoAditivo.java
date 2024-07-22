package ec.edu.uce.enums;

public enum TipoAditivo {
    OCTANE("Octane_Booster"),
    INJECTOR("Injector_Cleaner"),
    DINJERCTOR("Diesel_Injector");

    private String nombre;

    TipoAditivo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
