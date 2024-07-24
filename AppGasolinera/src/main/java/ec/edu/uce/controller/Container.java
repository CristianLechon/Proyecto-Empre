package ec.edu.uce.controller;

import ec.edu.uce.model.*;
import ec.edu.uce.util.DispatcherContext;
import ec.edu.uce.util.TipoAditivo;
import ec.edu.uce.util.TipoGasolina;
import ec.edu.uce.view.ClientLoginFrame;
import ec.edu.uce.view.GasolinaFrame;

import javax.swing.*;
import java.util.List;


public class Container {

    private float precioPorGalon, galonesComprados, precioAditivo;
    private int aditivoComprado;
    private GasolineraManager gasolineraManager;
    private AditivoManager aditivoManager;

    public Container() {

        aditivoManager = new AditivoManager();
        gasolineraManager = new GasolineraManager();

    }

    public void loginAdmin(String username, String password) {
        if (AdminManager.adminExiste(username, password)) {
            JOptionPane.showMessageDialog(null, "Registro exitoso!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    public static boolean registerAdmin(String userName, String lastName, String password) {
        boolean success = AdminManager.registrarAdmin(userName, lastName, password);
        if (success) {
            JOptionPane.showMessageDialog(null, "Registro exitoso!");
        } else {
            JOptionPane.showMessageDialog(null, "El Administrador ya existe.");
        }
        return success;
    }

    public void loginDispatcher(String userName, String password) {
        if (DispatcherManager.dispacherExiste(userName, password)) {
            JOptionPane.showMessageDialog(null, "Login exitoso!");
            Dispatcher dispatcher = DispatcherManager.obtenerDispatcherPorUsername(userName);
            DispatcherContext.setCurrentDispatcher(dispatcher);
            new ClientLoginFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    public static boolean registerDispatcher(String username, String lastName, String password) {
        boolean success = DispatcherManager.registrarDispatcher(username, lastName, password);
        if (success) {
            JOptionPane.showMessageDialog(null, "Registro exitoso!");
        } else {
            JOptionPane.showMessageDialog(null, "El despachador ya existe.");
        }
        return success;
    }

    public float obtenerPrecioPorLitro(TipoGasolina tipoGasolina) {
        List<Gasolina> gasolinas = gasolineraManager.getListaGasolinas();
        for (Gasolina gasolina : gasolinas) {
            if (gasolina.getTipoGasolina() == tipoGasolina) {
                return gasolina.getPrecio();
            }
        }
        return 0.0f;
    }

    public float obtenerPrecioAditivo(TipoAditivo tipoAditivo){
        List<Aditivo> aditivos = aditivoManager.getListaAditivos();
        for (Aditivo aditivo : aditivos) {
            if (aditivo.getTipoAditivo() == tipoAditivo) {
                return aditivo.getPrecio();
            }
        }
        return 0.0f;
    }

}
