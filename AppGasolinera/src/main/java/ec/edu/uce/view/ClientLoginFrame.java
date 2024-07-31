package ec.edu.uce.view;

import ec.edu.uce.model.Client;
import ec.edu.uce.model.ClientManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientLoginFrame extends JFrame {
    private JTextField nombreField, lastNameField, matriculaField, ciField;
    private ClientManager clientManager;
    private JPanel panel;
    private JLabel matriculaLabel, nombreLabel, lastNameLabel, ciLabel;

    public ClientLoginFrame() {
        clientManager = new ClientManager();

        setTitle("Login - Usuario");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Panel principal con fondo degradado
        panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                // Definir los colores del degradado usando colores del logo
                Color color1 = Color.decode("#FFD700"); // Amarillo
                Color color2 = Color.decode("#000080"); // Azul
                // Crear el degradado
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir el logo de ANETA redimensionado
        JLabel logoLabel = new JLabel();
        try {
            BufferedImage logoImage = ImageIO.read(new File("src/main/resources/imagenes/anetaLog.png"));
            if (logoImage != null) {
                Image scaledLogoImage = logoImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(scaledLogoImage));
            } else {
                System.out.println("El logo de ANETA no se pudo cargar. Verifica la ruta del archivo.");
            }
        } catch (IOException e) {
            System.out.println("Error al cargar la imagen del logo: " + e.getMessage());
            e.printStackTrace();
        }
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(logoLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setOpaque(false);

        // Añadir componentes al panel
        matriculaLabel = new JLabel("Matrícula:");
        matriculaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        matriculaLabel.setForeground(Color.WHITE);
        matriculaField = new JTextField();
        matriculaField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        matriculaField.setFont(new Font("Arial", Font.PLAIN, 14));
        matriculaField.setPreferredSize(new Dimension(200, 30));

        // Añadir texto guía para la matrícula
        JLabel guiaLabel = new JLabel("Añadir la placa sin guion por ejemplo ABC123");
        guiaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        guiaLabel.setForeground(Color.WHITE);

        JButton checkButton = new RoundedButton("Check Matrícula", "#FFD700");
        checkButton.addActionListener(e -> checkMatricula());

        formPanel.add(matriculaLabel);
        formPanel.add(matriculaField);
        formPanel.add(guiaLabel);
        formPanel.add(checkButton);

        panel.add(formPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    private void checkMatricula() {
        String matricula = matriculaField.getText().trim();
        Client cliente = clientManager.buscarClientePorMatricula(matricula);

        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "Matrícula encontrada, Cliente: " + cliente.getName() + " " + cliente.getLastName());
            new GasolinaFrame(cliente);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Matrícula no encontrada. Registre la matrícula.");

            // Limpiar formPanel
            panel.remove(1);

            JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            formPanel.setOpaque(false);

            formPanel.add(matriculaLabel);
            formPanel.add(matriculaField);

            nombreLabel = new JLabel("Nombre:");
            nombreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            nombreLabel.setForeground(Color.WHITE);
            nombreField = new JTextField();
            nombreField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
            nombreField.setPreferredSize(new Dimension(200, 30));

            lastNameLabel = new JLabel("Apellido:");
            lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            lastNameLabel.setForeground(Color.WHITE);
            lastNameField = new JTextField();
            lastNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            lastNameField.setFont(new Font("Arial", Font.PLAIN, 14));
            lastNameField.setPreferredSize(new Dimension(200, 30));

            ciLabel = new JLabel("CI:");
            ciLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            ciLabel.setForeground(Color.WHITE);
            ciField = new JTextField();
            ciField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            ciField.setFont(new Font("Arial", Font.PLAIN, 14));
            ciField.setPreferredSize(new Dimension(200, 30));

            formPanel.add(nombreLabel);
            formPanel.add(nombreField);
            formPanel.add(lastNameLabel);
            formPanel.add(lastNameField);
            formPanel.add(ciLabel);
            formPanel.add(ciField);

            JButton registerButton = new RoundedButton("Register", "#FF0000");
            registerButton.addActionListener(e -> register());
            formPanel.add(new JLabel());
            formPanel.add(registerButton);

            panel.add(formPanel, BorderLayout.CENTER);

            // Revalidar y repintar el panel
            panel.revalidate();
            panel.repaint();
        }
    }

    private void register() {
        String name = nombreField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String plate = matriculaField.getText().trim();
        String CI = ciField.getText().trim();

        boolean registrado = clientManager.registrarCliente(name, lastName, plate, CI);

        if (registrado) {
            JOptionPane.showMessageDialog(this, "Registro exitoso!");
            new ClientLoginFrame();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "La matrícula ya está registrada.");
        }
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
            setFont(new Font("Arial", Font.BOLD, 14));
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
}
