package ec.edu.uce.model;

public class Cliente {
    private String nombre;
    private String matricula;
    private String CI;

    public Cliente(String nombre, String matricula, String CI) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.CI = CI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCI() {
        return CI;
    }
}


