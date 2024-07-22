package ec.edu.uce.view;

import ec.edu.uce.controller.Container;
import ec.edu.uce.enums.TipoGasolina;
import ec.edu.uce.model.GasolineraManager;

import javax.swing.*;
import java.awt.*;

public class RecargaFrame extends JFrame {

    private GasolineraManager gasolineraManager;
    private TipoGasolina tipoGasolina;
    private JTextField precioField;
    private JLabel galonesLabel;
    private JTextArea progresoArea;
    private Container container;
    private float galonesComprados;
    private float precio;

    public RecargaFrame(GasolineraManager gasolinera, TipoGasolina tipoGasolina) {
        this.gasolineraManager = gasolinera;
        this.tipoGasolina = tipoGasolina;

        container = new Container();
        setTitle("Recarga de " + tipoGasolina.getNombre());
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Panel principal con fondo degradado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = Color.decode("#FFD700");
                Color color2 = Color.decode("#000080");
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta de instrucciones
        JLabel instrucciones = new JLabel("Ingrese el monto en dólares para recargar:");
        instrucciones.setFont(new Font("Segoe UI", Font.BOLD, 16));
        instrucciones.setForeground(Color.WHITE);
        instrucciones.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(instrucciones, gbc);

        // Campo para el monto de recarga
        precioField = new JTextField();
        precioField.setPreferredSize(new Dimension(200, 30));
        precioField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        precioField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(precioField, gbc);

        // Etiqueta de galones disponibles
        galonesLabel = new JLabel();
        galonesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        galonesLabel.setForeground(Color.WHITE);
        galonesLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        actualizarGalonesLabel();
        mainPanel.add(galonesLabel, gbc);

        // Botón de recarga
        JButton recargarButton = new RoundedButton("Recargar", "#FFD700");
        recargarButton.setPreferredSize(new Dimension(200, 30));
        recargarButton.addActionListener(e -> {
            precio = Float.parseFloat(precioField.getText());
            container.recargar(precio,gasolineraManager,tipoGasolina);
            galonesComprados = precio / gasolineraManager.obtenerPrecio(tipoGasolina);
            actualizarGalonesLabel();
            iniciarRecarga(galonesComprados);

        });
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(recargarButton, gbc);

        // Área de progreso
        JPanel progresoPanel = new JPanel();
        progresoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Progreso de Recarga", 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        progresoPanel.setLayout(new BorderLayout());
        progresoPanel.setBackground(new Color(0, 0, 0, 0)); // Transparente
        progresoArea = new JTextArea();
        progresoArea.setEditable(false);
        progresoArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(progresoArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        progresoPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(progresoPanel, gbc);

        setVisible(true);
    }

    private void iniciarRecarga(float galones) {
        progresoArea.setText("");

        SwingWorker<Void, Float> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                float incremento = 0.1f;
                float progreso = 0f;

                while (progreso < galones) {
                    progreso += incremento;
                    publish(progreso);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void process(java.util.List<Float> chunks) {
                if (!chunks.isEmpty()) {
                    float value = chunks.get(chunks.size() - 1);
                    progresoArea.setText(String.format(" %.2f galones", value));
                }
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(RecargaFrame.this, "Recarga completa", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        worker.execute();
    }

    private void actualizarGalonesLabel() {
        float galonesDisponibles = gasolineraManager.obtenerGalones(tipoGasolina);
        galonesLabel.setText("Galones disponibles: " + galonesDisponibles);
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

    public float getPrecio() {
        return precio;
    }
}
