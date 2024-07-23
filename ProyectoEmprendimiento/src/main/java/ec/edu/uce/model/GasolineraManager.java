package ec.edu.uce.model;

import ec.edu.uce.enums.TipoAditivo;
import ec.edu.uce.enums.TipoGasolina;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GasolineraManager {
    private Map<TipoGasolina, Float> precios;
    private Map<TipoGasolina, Float> galonesDisponibles;
    private Map<TipoAditivo, Float> preciosAditivos;
    private Map<TipoAditivo, Integer> unidades;
    private final String filePath = "src/main/resources/precios_gasolina.txt";
    private final String fileAditivos = "src/main/resources/precios_aditivos.txt";

    public GasolineraManager() {
        this.precios = new HashMap<>();
        this.galonesDisponibles = new HashMap<>();
        this.preciosAditivos = new HashMap<>();
        this.unidades = new HashMap<>();
        cargarDatos();
        cargarAditivos();
    }

    private void cargarAditivos() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileAditivos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3) {
                    String tipoStr = partes[0].trim();
                    float precio = Float.parseFloat(partes[1].trim());
                    int unidad = Integer.parseInt(partes[2].trim());

                    // Convertir el tipo de String a TipoGasolina usando un switch o if
                    TipoAditivo tipoAditivo = null;
                    switch (tipoStr.toLowerCase()) {
                        case "octane_booster":
                            tipoAditivo = TipoAditivo.OCTANE_BOOSTER;
                            break;
                        case "injector_cleaner":
                            tipoAditivo = TipoAditivo.INJECTOR_CLEANER;
                            break;
                        case "diesel_injector":
                            tipoAditivo = TipoAditivo.DIESEL_INJECTOR;
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de aditivo no encontrado: " + tipoStr);
                    }


                    preciosAditivos.put(tipoAditivo, precio);
                    unidades.put(tipoAditivo, unidad);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(":");
                if (partes.length == 3) {
                    String tipoStr = partes[0].trim();
                    float precio = Float.parseFloat(partes[1].trim());
                    float galones = Float.parseFloat(partes[2].trim());

                    // Convertir el tipo de String a TipoGasolina usando un switch o if
                    TipoGasolina tipoGasolina = null;
                    switch (tipoStr.toLowerCase()) {
                        case "super":
                            tipoGasolina = TipoGasolina.SUPER;
                            break;
                        case "extra":
                            tipoGasolina = TipoGasolina.EXTRA;
                            break;
                        case "diesel":
                            tipoGasolina = TipoGasolina.DIESEL;
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de gasolina o aditivo no encontrado: " + tipoStr);
                    }

                    precios.put(tipoGasolina, precio);
                    galonesDisponibles.put(tipoGasolina, galones);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float obtenerPrecioAditivo(TipoAditivo tipoAditivo) {
        return preciosAditivos.getOrDefault(tipoAditivo, 0.0f);
    }

    public int obtenerUnidades(TipoAditivo tipoAditivo) {
        return unidades.getOrDefault(tipoAditivo, 0);
    }

    public void actualizarUnidades(TipoAditivo tipoAditivo, int unidadesVendidas) {
        int unidadesRestantes = unidades.getOrDefault(tipoAditivo, 0) - unidadesVendidas;
        unidades.put(tipoAditivo, unidadesRestantes);
        guardarDatosAditivos();
    }

    private void guardarDatosAditivos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileAditivos))) {
            for (TipoAditivo tipo : preciosAditivos.keySet()) {
                float precio = preciosAditivos.get(tipo);
                int unidad = unidades.get(tipo);
                bw.write(tipo.name() + ":" + precio + ":" + unidad);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float obtenerPrecio(TipoGasolina tipoGasolina) {
        return precios.getOrDefault(tipoGasolina, 0.0f);
    }

    public float obtenerGalones(TipoGasolina tipoGasolina) {
        return galonesDisponibles.getOrDefault(tipoGasolina, 0.0f);
    }

    public void actualizarGalones(TipoGasolina tipoGasolina, float galonesVendidos) {
        float galonesRestantes = galonesDisponibles.getOrDefault(tipoGasolina, 0.0f) - galonesVendidos;
        galonesDisponibles.put(tipoGasolina, galonesRestantes);
        guardarDatos();
    }

    private void guardarDatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (TipoGasolina tipo : precios.keySet()) {
                float precio = precios.get(tipo);
                float galones = galonesDisponibles.get(tipo);
                bw.write(tipo.name() + ":" + precio + ":" + galones);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
