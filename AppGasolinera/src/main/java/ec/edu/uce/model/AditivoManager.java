package ec.edu.uce.model;

import ec.edu.uce.util.TipoAditivo;
import ec.edu.uce.util.TipoGasolina;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AditivoManager {
    private List<Aditivo> listaAditivos = new ArrayList<>();
    private static final String aditivoFile = "src/main/resources/precios_aditivos.txt";

    public AditivoManager() {
        cargarDatosDesdeArchivo();
    }

    public void cargarDatosDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(aditivoFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3) {
                    String tipoAditivoStr = partes[0];
                    float precio = Float.parseFloat(partes[1]);
                    int unidades = Integer.parseInt(partes[2]);

                    TipoAditivo tipoAditivo = TipoAditivo.valueOf(tipoAditivoStr);

                    Aditivo aditivo = new Aditivo(tipoAditivo, precio, unidades);
                    listaAditivos.add(aditivo);
                } else {
                    System.out.println("Formato de línea incorrecto: " + linea);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public int returnAditivos(TipoAditivo tipoAditivo) {
        int totalAditivos = 0;
        for (Aditivo aditivo : listaAditivos) {
            if (aditivo.getTipoAditivo() == tipoAditivo) {
                totalAditivos += aditivo.getUnidades();
            }
        }
        return totalAditivos;
    }

    public Aditivo getAditivoPorNombre(TipoAditivo tipoAditivo) {
        for (Aditivo aditivo : listaAditivos) {
            if (aditivo.getTipoAditivo() == tipoAditivo) {
                return aditivo;
            }
        }
        return null; // Devuelve null si no se encuentra el aditivo
    }

    public int actualizarAditivos(TipoAditivo tipoAditivo, int nuevasUnidades) {
        boolean encontrado = false;
        StringBuilder nuevoContenido = new StringBuilder();

        for (Aditivo aditivo : listaAditivos) {
            if (aditivo.getTipoAditivo() == tipoAditivo) {
                aditivo.setUnidades(nuevasUnidades);
                encontrado = true;
            }
            nuevoContenido.append(aditivo.toString()).append("\n");
        }

        if (!encontrado) {
            System.out.println("Tipo de aditivo no encontrado: " + tipoAditivo);
            return 0;
        }

        // Escribir el nuevo contenido al archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(aditivoFile))) {
            writer.write(nuevoContenido.toString());
            System.out.println("Actualización exitosa de unidades para " + tipoAditivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
        return returnAditivos(tipoAditivo);
    }

    public List<Aditivo> getListaAditivos() {
        return listaAditivos;
    }
}
