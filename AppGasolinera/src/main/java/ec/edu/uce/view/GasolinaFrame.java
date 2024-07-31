package ec.edu.uce.view;

import ec.edu.uce.controller.Container;
import ec.edu.uce.model.*;
import ec.edu.uce.util.CarritoAditivos;
import ec.edu.uce.util.FacturaPDF;
import ec.edu.uce.util.TipoAditivo;
import ec.edu.uce.util.TipoGasolina;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GasolinaFrame extends JFrame {

    private JComboBox<String> comboBoxGasolina;
    private JComboBox<String> comboBoxAditivos;
    private JRadioButton galonesRadioButton, dineroRadioButton;
    private JTextField textFieldCantidad;
    private JTextField textFieldCantidadAditivo;
    private JButton buttonRecargar, buttonPagar, confirmarMetodoPagoButton;
    private JButton buttonAñadirAditivo;
    private GasolineraManager gasolineraManager;
    private Container container;
    private JPanel panelTarjeta, panelMetodoPago, panelPrincipal;
    private JRadioButton efectivoRadioButton, tarjetaRadioButton;
    private ButtonGroup paymentMethodGroup;
    private AditivoManager aditivoManager;
    private CarritoAditivos carritoAditivos;
    private JTextField numeroTarjetaField, fechaVencimientoField, cvvField;
    private JLabel galonesDisponiblesLabel;
    private Client cliente;

    public GasolinaFrame(Client cliente) {
        this.cliente = cliente;

        gasolineraManager = new GasolineraManager();
        aditivoManager = new AditivoManager();
        carritoAditivos = new CarritoAditivos();
        container = new Container();

        setTitle("Recarga de Gasolina");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelPrincipal.add(new JLabel("Tipo de Gasolina:"), gbc);

        gbc.gridx = 1;
        comboBoxGasolina = new JComboBox<>();
        cargarTiposGasolina();
        panelPrincipal.add(comboBoxGasolina, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Tipo de Aditivo:"), gbc);

        gbc.gridx = 1;
        comboBoxAditivos = new JComboBox<>();
        cargarTiposAditivos();
        panelPrincipal.add(comboBoxAditivos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Cantidad Aditivo:"), gbc);

        gbc.gridx = 1;
        textFieldCantidadAditivo = new JTextField(10);
        panelPrincipal.add(textFieldCantidadAditivo, gbc);

        gbc.gridx = 2;
        buttonAñadirAditivo = new JButton("Añadir Aditivo al Carrito");
        buttonAñadirAditivo.addActionListener(e -> añadirAditivoAlCarrito());
        panelPrincipal.add(buttonAñadirAditivo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelPrincipal.add(new JLabel("Tipo de Recarga:"), gbc);

        gbc.gridx = 1;
        JPanel panelRadio = new JPanel(new FlowLayout());
        galonesRadioButton = new JRadioButton("Galones");
        dineroRadioButton = new JRadioButton("Dinero");
        ButtonGroup group = new ButtonGroup();
        group.add(galonesRadioButton);
        group.add(dineroRadioButton);
        galonesRadioButton.setSelected(true);
        panelRadio.add(galonesRadioButton);
        panelRadio.add(dineroRadioButton);
        panelPrincipal.add(panelRadio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelPrincipal.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        textFieldCantidad = new JTextField(10);
        panelPrincipal.add(textFieldCantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelPrincipal.add(new JLabel("Método de Pago:"), gbc);

        gbc.gridx = 1;
        panelMetodoPago = new JPanel(new FlowLayout());
        efectivoRadioButton = new JRadioButton("Efectivo");
        tarjetaRadioButton = new JRadioButton("Tarjeta");
        paymentMethodGroup = new ButtonGroup();
        paymentMethodGroup.add(efectivoRadioButton);
        paymentMethodGroup.add(tarjetaRadioButton);
        efectivoRadioButton.setSelected(true);
        panelMetodoPago.add(efectivoRadioButton);
        panelMetodoPago.add(tarjetaRadioButton);
        panelPrincipal.add(panelMetodoPago, gbc);

        gbc.gridx = 2;
        confirmarMetodoPagoButton = new JButton("Confirmar Método de Pago");
        confirmarMetodoPagoButton.addActionListener(e -> mostrarCamposTarjeta());
        panelPrincipal.add(confirmarMetodoPagoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panelTarjeta = new JPanel(new GridLayout(3, 2));
        numeroTarjetaField = new JTextField(16);
        fechaVencimientoField = new JTextField(5);
        cvvField = new JTextField(3);
        panelTarjeta.add(new JLabel("Número de Tarjeta:"));
        panelTarjeta.add(numeroTarjetaField);
        panelTarjeta.add(new JLabel("Fecha de Vencimiento (MM/AA):"));
        panelTarjeta.add(fechaVencimientoField);
        panelTarjeta.add(new JLabel("CVV:"));
        panelTarjeta.add(cvvField);
        panelTarjeta.setVisible(false);
        panelPrincipal.add(panelTarjeta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        buttonRecargar = new JButton("Recargar");
        buttonRecargar.addActionListener(e -> recargar());
        panelPrincipal.add(buttonRecargar, gbc);

        gbc.gridx = 1;
        buttonPagar = new JButton("Pagar");
        buttonPagar.addActionListener(e -> pagar());
        panelPrincipal.add(buttonPagar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        galonesDisponiblesLabel = new JLabel();
        panelPrincipal.add(galonesDisponiblesLabel, gbc);

        actualizarGalonesLabel();

        add(panelPrincipal);
        setVisible(true);
    }

    private void cargarTiposGasolina() {
        List<Gasolina> gasolinas = gasolineraManager.getListaGasolinas();
        for (Gasolina gasolina : gasolinas) {
            comboBoxGasolina.addItem(gasolina.getTipoGasolina().toString());
        }
    }

    private void cargarTiposAditivos() {
        List<Aditivo> aditivos = aditivoManager.getListaAditivos();
        for (Aditivo aditivo : aditivos) {
            comboBoxAditivos.addItem(aditivo.getTipoAditivo().toString());
        }
    }

    private void añadirAditivoAlCarrito() {
        String aditivoNombre = (String) comboBoxAditivos.getSelectedItem();
        Aditivo aditivo = aditivoManager.getAditivoPorNombre(TipoAditivo.valueOf(aditivoNombre));
        if (aditivo == null) {
            JOptionPane.showMessageDialog(this, "Aditivo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cantidadStr = textFieldCantidadAditivo.getText();
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int recargarCant = cantidad;
        int nuevosGalones = aditivoManager.returnAditivos(TipoAditivo.valueOf(aditivoNombre)) - recargarCant;
        aditivoManager.actualizarAditivos(TipoAditivo.valueOf(aditivoNombre),nuevosGalones);
        carritoAditivos.agregarAditivo(aditivo, recargarCant);
    }

    private void recargar() {
        String tipoGasolinaStr = (String) comboBoxGasolina.getSelectedItem();
        TipoGasolina tipoGasolina = TipoGasolina.valueOf(tipoGasolinaStr);

        String cantidadStr = textFieldCantidad.getText();
        float cantidad;
        try {
            cantidad = Float.parseFloat(cantidadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (galonesRadioButton.isSelected()) {
            float galonesRecargar = cantidad;
            float precioPorGalon = container.obtenerPrecioPorLitro(tipoGasolina);
            float montoTotal = galonesRecargar * precioPorGalon + carritoAditivos.calcularTotalAditivos();
            mostrarMensajeRecarga(galonesRecargar, tipoGasolina, montoTotal);
        } else if (dineroRadioButton.isSelected()) {
            confirmarMetodoPagoButton.setVisible(true);
        }
    }

    private void mostrarCamposTarjeta() {
        if (tarjetaRadioButton.isSelected()) {
            panelTarjeta.setVisible(true);
        } else {
            panelTarjeta.setVisible(false);
            pagar();
        }
    }

    private void pagar() {
        if (tarjetaRadioButton.isSelected() && !validarDatosTarjeta()) {
            return;
        }

        String tipoGasolinaStr = (String) comboBoxGasolina.getSelectedItem();
        TipoGasolina tipoGasolina = TipoGasolina.valueOf(tipoGasolinaStr);

        float dineroRecargar = Float.parseFloat(textFieldCantidad.getText());
        float precioPorLitro = container.obtenerPrecioPorLitro(tipoGasolina);
        float litrosRecargar = dineroRecargar / precioPorLitro;
        float iva = dineroRecargar * 0.15f;
        float total = dineroRecargar - iva;
        float total_iva = total + iva;

        float galones = gasolineraManager.returnGalones(tipoGasolina);
        float nuevosGalones = gasolineraManager.returnGalones(tipoGasolina) - litrosRecargar;
        gasolineraManager.actualizarGalonesDisponibles(tipoGasolina, nuevosGalones);
        float totalAditivos = carritoAditivos.calcularTotalAditivos();
        float montoTotal = dineroRecargar + totalAditivos;
        mostrarMensajeRecarga(litrosRecargar, tipoGasolina, montoTotal);

        String metodoPago = tarjetaRadioButton.isSelected() ? "Tarjeta" : "Efectivo";
        String numeroTarjeta = tarjetaRadioButton.isSelected() ? numeroTarjetaField.getText() : "";

        //FacturaPDF.generarFactura(cliente, despachador, tipoGasolina, precioPorLitro, galones, total, iva, total_iva, metodoPago, totalAditivos, numeroTarjeta);

        actualizarGalonesLabel();
    }


    private boolean validarDatosTarjeta() {
        String numeroTarjeta = numeroTarjetaField.getText();
        String fechaVencimiento = fechaVencimientoField.getText();
        String cvv = cvvField.getText();

        if (numeroTarjeta.isEmpty() || fechaVencimiento.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos de la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (numeroTarjeta.length() != 16 || !numeroTarjeta.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Número de tarjeta inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (fechaVencimiento.length() != 5 || !fechaVencimiento.matches("\\d{2}/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Fecha de vencimiento inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "CVV inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void actualizarGalonesLabel() {
        String tipoGasolinaStr = (String) comboBoxGasolina.getSelectedItem();
        TipoGasolina tipoGasolina = TipoGasolina.valueOf(tipoGasolinaStr);

        float galonesDisponibles = gasolineraManager.returnGalones(tipoGasolina);

        galonesDisponiblesLabel.setText("Galones disponibles de " + tipoGasolina + ": " + galonesDisponibles);
    }

    private void mostrarMensajeRecarga(double cantidad, TipoGasolina tipoGasolina, float montoTotal) {
        JOptionPane.showMessageDialog(this, "Se ha recargado " + cantidad + " galones de " + tipoGasolina + ". Monto total a pagar: $" + montoTotal, "Recarga Completa", JOptionPane.INFORMATION_MESSAGE);
        buttonPagar.setEnabled(true);
    }

}
