package ec.edu.uce.model;

import ec.edu.uce.util.TipoGasolina;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GasolineraManager {
    private List<Gasolina> listaGasolinas = new ArrayList<>();
    private static final String gasolinaFile = "src/main/resources/precios_gasolina.txt";


    public GasolineraManager() {
        cargarDatosDesdeArchivo();
    }

    public void cargarDatosDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(gasolinaFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3) {
                    String tipoGasolinaStr = partes[0];
                    float precio = Float.parseFloat(partes[1]);
                    float galones = Float.parseFloat(partes[2]);

                    TipoGasolina tipoGasolina = TipoGasolina.valueOf(tipoGasolinaStr);

                    Gasolina gasolina = new Gasolina(tipoGasolina, precio, galones);
                    listaGasolinas.add(gasolina);
                } else {
                    System.out.println("Formato de línea incorrecto: " + linea);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public float returnGalones(TipoGasolina tipoGasolina) {
        float totalGalones = 0.0f;
        for (Gasolina gasolina : listaGasolinas) {
            if (gasolina.getTipoGasolina() == tipoGasolina) {
                totalGalones += gasolina.getGalones();
            }
        }
        return totalGalones;
    }

    public float actualizarGalonesDisponibles(TipoGasolina tipoGasolina, float nuevosGalones) {
        boolean encontrado = false;
        StringBuilder nuevoContenido = new StringBuilder();

        for (Gasolina gasolina : listaGasolinas) {
            if (gasolina.getTipoGasolina() == tipoGasolina) {
                gasolina.setGalones(nuevosGalones);
                encontrado = true;
            }
            nuevoContenido.append(gasolina.toString()).append("\n");
        }

        if (!encontrado) {
            System.out.println("Tipo de gasolina no encontrado: " + tipoGasolina);
            return 0.0f;
        }

        // Escribir el nuevo contenido al archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(gasolinaFile))) {
            writer.write(nuevoContenido.toString());
            System.out.println("Actualización exitosa de galones para " + tipoGasolina);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }

        return returnGalones(tipoGasolina);
    }

    public List<Gasolina> getListaGasolinas() {
        return listaGasolinas;
    }
}
