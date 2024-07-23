package ec.edu.uce.controller;

import ec.edu.uce.enums.TipoAditivo;
import ec.edu.uce.enums.TipoGasolina;
import ec.edu.uce.model.AdminManager;
import ec.edu.uce.model.Dispatcher;
import ec.edu.uce.model.DispatcherManager;
import ec.edu.uce.model.GasolineraManager;
import ec.edu.uce.view.AdminMetod;
import ec.edu.uce.view.UserLoginFrame;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Container {

    private GasolineraManager gasolineraManager;
    private float precioPorGalon, galonesComprados, precioAditivo;
    private int aditivoComprado;
    private Map<TipoAditivo, Integer> aditivosCarrito = new HashMap<>();

    public Container() {
        this.gasolineraManager = new GasolineraManager();
    }

    public void loginAdmin(String username, String password) {
        if (AdminManager.validarUsuario(username, password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            new AdminMetod();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    public void registerAdmin(String username, String password) {
        boolean registrado = AdminManager.registrarAdmin(username, password);

        if (registrado) {
            JOptionPane.showMessageDialog(null, "Registration successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Username already exists.");
        }
    }

    public void loginDispatcher(String username, String password) {
        if (DispatcherManager.validarUsuarioDispatcher(username, password)) {
            Dispatcher dispatcher = DispatcherManager.obtenerDispatcherPorUsername(username);
            DispatcherContext.setCurrentDispatcher(dispatcher);
            new UserLoginFrame();
            JOptionPane.showMessageDialog(null, "Login successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.");
        }
    }

    public boolean registerDispatcher(String username, String lastName, String password) {
        boolean success = DispatcherManager.registrarDispatcher(username, lastName, password);
        if (success) {
            JOptionPane.showMessageDialog(null, "Registro exitoso!");
        } else {
            JOptionPane.showMessageDialog(null, "El usuario ya existe.");
        }
        return success;
    }

    public void recargar(float precio, GasolineraManager gasolinera, TipoGasolina tipoGasolina) {
        try {
            precioPorGalon = gasolineraManager.obtenerPrecio(tipoGasolina);
            galonesComprados = precio / precioPorGalon;

            if (galonesComprados >= gasolinera.obtenerGalones(tipoGasolina)) {
                JOptionPane.showMessageDialog(null, "No hay suficientes galones disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                gasolinera.actualizarGalones(tipoGasolina, galonesComprados);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void Comprar(int cantidad, GasolineraManager gasolinera, TipoAditivo tipoAditivo) {
        try {
            precioAditivo = gasolineraManager.obtenerPrecioAditivo(tipoAditivo);
            aditivoComprado = cantidad;

            if (aditivoComprado >= gasolinera.obtenerUnidades(tipoAditivo)) {
                JOptionPane.showMessageDialog(null, "No hay suficientes unidades disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //gasolinera.actualizarGalones(tipoGasolina, galonesComprados);
                gasolinera.actualizarUnidades(tipoAditivo, aditivoComprado);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un valor válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarAditivo(TipoAditivo aditivo, int cantidad) {
        aditivosCarrito.put(aditivo, cantidad);
    }

    public Map<TipoAditivo, Integer> obtenerAditivosCarrito() {
        return aditivosCarrito;
    }
}