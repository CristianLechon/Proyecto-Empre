package ec.edu.uce.view;

import ec.edu.uce.controller.Container;
import ec.edu.uce.enums.TipoAditivo;
import ec.edu.uce.enums.TipoGasolina;
import ec.edu.uce.model.Aditivo;
import ec.edu.uce.model.Cliente;
import ec.edu.uce.model.GasolineraManager;
import ec.edu.uce.util.FacturaPDF;
import ec.edu.uce.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GasolinaFrame extends JFrame {

    private GasolineraManager gasolinera;
    private Cliente cliente;
    private RecargaFrame recargaFrame;
    private float precio, precioPorGalon, galonesComprados, iva, total, total_iva;
    private JRadioButton efectivoRadioButton;
    private JRadioButton tarjetaRadioButton;
    private JTextField numeroTarjetaField;
    private JTextField fechaVencimientoField;
    private JTextField cvvField;
    private JPanel tarjetaPanel;
    private Container container;
    private int cantidadAditivo;
    private List<TipoAditivo> aditivosSeleccionados;

    public GasolinaFrame(Cliente cliente) {
        this.cliente = cliente;
        gasolinera = new GasolineraManager();
        aditivosSeleccionados = new ArrayList<>();

        setTitle("Gasolinera ANETA");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Panel principal con bordes curvos y fondo degradado
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color color1 = Color.decode("#FFD700");
                Color color2 = Color.decode("#000080");
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        mainPanel.setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);

        // Panel para opciones de gasolina
        JPanel gasolinaPanel = new JPanel(new GridBagLayout());
        gasolinaPanel.setOpaque(false);
        gasolinaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(gasolinaPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta para selección de tipo de gasolina
        JLabel tipoGasolinaLabel = new JLabel("Seleccione tipo de gasolina:");
        tipoGasolinaLabel.setForeground(Color.WHITE);
        tipoGasolinaLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gasolinaPanel.add(tipoGasolinaLabel, gbc);

        // ComboBox para tipos de gasolina
        JComboBox<TipoGasolina> tipoGasolinaComboBox = new JComboBox<>(TipoGasolina.values());
        gbc.gridx = 1;
        gasolinaPanel.add(tipoGasolinaComboBox, gbc);

        // Etiqueta para selección de tipo de pago
        JLabel tipoPagoLabel = new JLabel("Seleccione tipo de pago:");
        tipoPagoLabel.setForeground(Color.WHITE);
        tipoPagoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gasolinaPanel.add(tipoPagoLabel, gbc);

        // Botones de radio para tipos de pago
        efectivoRadioButton = new JRadioButton("Efectivo");
        tarjetaRadioButton = new JRadioButton("Tarjeta");

        efectivoRadioButton.setOpaque(false);
        efectivoRadioButton.setForeground(Color.WHITE);
        tarjetaRadioButton.setOpaque(false);
        tarjetaRadioButton.setForeground(Color.WHITE);

        ButtonGroup tipoPagoGroup = new ButtonGroup();
        tipoPagoGroup.add(efectivoRadioButton);
        tipoPagoGroup.add(tarjetaRadioButton);

        JPanel tipoPagoPanel = new JPanel(new GridLayout(1, 2));
        tipoPagoPanel.setOpaque(false);
        tipoPagoPanel.add(efectivoRadioButton);
        tipoPagoPanel.add(tarjetaRadioButton);

        gbc.gridx = 1;
        gasolinaPanel.add(tipoPagoPanel, gbc);

        // Panel para información de tarjeta
        tarjetaPanel = new JPanel(new GridBagLayout());
        tarjetaPanel.setOpaque(false);
        tarjetaPanel.setVisible(false);

        JLabel numeroTarjetaLabel = new JLabel("Número de Tarjeta:");
        numeroTarjetaLabel.setForeground(Color.WHITE);
        numeroTarjetaField = new JTextField(16);
        numeroTarjetaField.setOpaque(false);

        JLabel fechaVencimientoLabel = new JLabel("Fecha de Vencimiento (MM/AA):");
        fechaVencimientoLabel.setForeground(Color.WHITE);
        fechaVencimientoField = new JTextField(5);
        fechaVencimientoField.setOpaque(false);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setForeground(Color.WHITE);
        cvvField = new JTextField(3);
        cvvField.setOpaque(false);

        GridBagConstraints gbcTarjeta = new GridBagConstraints();
        gbcTarjeta.insets = new Insets(5, 5, 5, 5);
        gbcTarjeta.fill = GridBagConstraints.HORIZONTAL;
        gbcTarjeta.gridx = 0;
        gbcTarjeta.gridy = 0;
        tarjetaPanel.add(numeroTarjetaLabel, gbcTarjeta);
        gbcTarjeta.gridx = 1;
        tarjetaPanel.add(numeroTarjetaField, gbcTarjeta);

        gbcTarjeta.gridx = 0;
        gbcTarjeta.gridy = 1;
        tarjetaPanel.add(fechaVencimientoLabel, gbcTarjeta);
        gbcTarjeta.gridx = 1;
        tarjetaPanel.add(fechaVencimientoField, gbcTarjeta);

        gbcTarjeta.gridx = 0;
        gbcTarjeta.gridy = 2;
        tarjetaPanel.add(cvvLabel, gbcTarjeta);
        gbcTarjeta.gridx = 1;
        tarjetaPanel.add(cvvField, gbcTarjeta);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gasolinaPanel.add(tarjetaPanel, gbc);

        // Panel para selección de aditivos
        JPanel aditivosPanel = new JPanel();
        aditivosPanel.setLayout(new BoxLayout(aditivosPanel, BoxLayout.Y_AXIS));
        aditivosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Seleccionar Aditivos", 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));

// ComboBox para seleccionar aditivos
        JComboBox<TipoAditivo> aditivosComboBox = new JComboBox<>(TipoAditivo.values());
        aditivosComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aditivosComboBox.setPreferredSize(new Dimension(200, 30));
        aditivosPanel.add(new JLabel("Seleccione el aditivo:"));
        aditivosPanel.add(aditivosComboBox);

// Campo para la cantidad del aditivo
        JTextField cantidadAditivoField = new JTextField();
        cantidadAditivoField.setPreferredSize(new Dimension(200, 30));
        cantidadAditivoField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cantidadAditivoField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        aditivosPanel.add(new JLabel("Cantidad del aditivo:"));
        aditivosPanel.add(cantidadAditivoField);

// Agregar panel de aditivos al panel principal
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(aditivosPanel, gbc);

// Botón para agregar aditivo
        JButton agregarAditivoButton = new RoundedButton("Agregar Aditivo", "#FFD700");
        agregarAditivoButton.setPreferredSize(new Dimension(200, 30));
        agregarAditivoButton.addActionListener(e -> {
            TipoAditivo aditivoSeleccionado = (TipoAditivo) aditivosComboBox.getSelectedItem();
            int cantidad = Integer.parseInt(cantidadAditivoField.getText());

            aditivosSeleccionados.add(aditivoSeleccionado);

            JOptionPane.showMessageDialog(GasolinaFrame.this, "Aditivo agregado al carrito: " + aditivoSeleccionado + ", Cantidad: " + cantidad, "Aditivo Agregado", JOptionPane.INFORMATION_MESSAGE);
        });
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(agregarAditivoButton, gbc);

        // Manejador de eventos para mostrar u ocultar el panel de tarjeta
        tarjetaRadioButton.addActionListener(e -> tarjetaPanel.setVisible(true));
        efectivoRadioButton.addActionListener(e -> tarjetaPanel.setVisible(false));

        // Botón para recargar
        JButton recargarButton = new RoundedButton("Recargar", "#008000"); // Verde oscuro
        recargarButton.addActionListener(e -> {
            TipoGasolina tipoGasolina = (TipoGasolina) tipoGasolinaComboBox.getSelectedItem();
            recargaFrame = new RecargaFrame(gasolinera, tipoGasolina);
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gasolinaPanel.add(recargarButton, gbc);

        // Botón para generar factura
        JButton facturaButton = new RoundedButton("Generar Factura", "#0000FF"); // Azul oscuro
        facturaButton.addActionListener(e -> {
            TipoGasolina tipoGasolina = (TipoGasolina) tipoGasolinaComboBox.getSelectedItem();
            String tipoPago = efectivoRadioButton.isSelected() ? "Efectivo" : "Tarjeta";
            precio = recargaFrame.getPrecio();
            precioPorGalon = gasolinera.obtenerPrecio(tipoGasolina);
            galonesComprados = (precio / precioPorGalon);
            iva = precio * 0.15f;
            total = precio - iva;
            total_iva = total + iva;

            // Validar datos de tarjeta si el tipo de pago es "Tarjeta"
            if (tarjetaRadioButton.isSelected()) {
                String numeroTarjeta = numeroTarjetaField.getText().trim();
                String fechaVencimiento = fechaVencimientoField.getText().trim();
                String cvv = cvvField.getText().trim();

                if (numeroTarjeta.isEmpty() || fechaVencimiento.isEmpty() || cvv.isEmpty()) {
                    JOptionPane.showMessageDialog(GasolinaFrame.this, "Por favor, complete todos los campos de la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                validarTarjeta(numeroTarjeta, fechaVencimiento, cvv);
            }

            // Generar la factura PDF
            FacturaPDF.generarFactura(cliente, gasolinera, tipoGasolina, precio, galonesComprados, total, iva, total_iva, tipoPago, aditivosSeleccionados);
            JOptionPane.showMessageDialog(GasolinaFrame.this, "Factura generada para: " + cliente.getNombre(), "Factura", JOptionPane.INFORMATION_MESSAGE);
        });
        gbc.gridy = 5;  // Asegúrate de que esta fila no esté ocupada por otros componentes
        gbc.gridwidth = 2;
        gasolinaPanel.add(facturaButton, gbc);

        // Etiqueta para mostrar la imagen del tipo de gasolina
        JLabel imagenLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gasolinaPanel.add(imagenLabel, gbc);

        // Actualizar la imagen cuando se selecciona un tipo de gasolina
        tipoGasolinaComboBox.addActionListener(e -> {
            TipoGasolina tipoGasolina = (TipoGasolina) tipoGasolinaComboBox.getSelectedItem();
            if (tipoGasolina != null) {
                String imagePath = "src/main/resources/imagenes/" + tipoGasolina.getNombre().toLowerCase() + ".png";
                ImageIcon icon = ImageLoader.loadImage(imagePath, 100, 100);
                if (icon != null) {
                    imagenLabel.setIcon(icon);
                }
            }
        });

        setVisible(true);
    }

    private boolean validarTarjeta(String numeroTarjeta, String fechaVencimiento, String cvv) {
        // Ejemplo de validación simple
        if (!numeroTarjeta.matches("\\d{16}")) {
            return false;
        }
        if (!fechaVencimiento.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        if (!cvv.matches("\\d{3}")) {
            return false;
        }
        return true;
    }


    // Clase interna para los botones redondeados
    private static class RoundedButton extends JButton {
        public RoundedButton(String text, String colorHex) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(Color.decode(colorHex));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setPreferredSize(new Dimension(120, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            super.paintComponent(g);
            g2d.dispose();
        }
    }

    public GasolineraManager getGasolinera() {
        return gasolinera;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public RecargaFrame getRecargaFrame() {
        return recargaFrame;
    }

    public float getPrecio() {
        return precio;
    }

    public float getPrecioPorGalon() {
        return precioPorGalon;
    }

    public float getGalonesComprados() {
        return galonesComprados;
    }

    public float getIva() {
        return iva;
    }

    public float getTotal() {
        return total;
    }

    public float getTotal_iva() {
        return total_iva;
    }
}
