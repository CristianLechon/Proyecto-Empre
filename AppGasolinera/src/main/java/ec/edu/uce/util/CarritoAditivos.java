package ec.edu.uce.util;

import ec.edu.uce.model.Aditivo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CarritoAditivos {
    private Map<Aditivo, Integer> aditivos;

    public CarritoAditivos() {
        aditivos = new HashMap<>();
    }

    public void agregarAditivo(Aditivo aditivo, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        aditivos.put(aditivo, aditivos.getOrDefault(aditivo, 0) + cantidad);
    }

    public Map<Aditivo, Integer> getAditivos() {
        return aditivos;
    }

    public float calcularTotalAditivos() {
        float total = 0;
        for (Map.Entry<Aditivo, Integer> entry : aditivos.entrySet()) {
            Aditivo aditivo = entry.getKey();
            int cantidad = entry.getValue();
            total += aditivo.getPrecio() * cantidad;
        }
        return total;
    }

    public void vaciarCarrito() {
        aditivos.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Aditivo, Integer>> entrySet = aditivos.entrySet();
        for (Map.Entry<Aditivo, Integer> entry : entrySet) {
            Aditivo aditivo = entry.getKey();
            int cantidad = entry.getValue();
            sb.append(aditivo.toString())
                    .append(" - Cantidad: ").append(cantidad)
                    .append("\n");
        }
        return sb.toString();
    }
}