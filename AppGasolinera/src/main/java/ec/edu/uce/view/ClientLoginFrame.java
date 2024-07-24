package ec.edu.uce.view;

import ec.edu.uce.model.Client;
import ec.edu.uce.model.ClientManager;
import ec.edu.uce.model.Dispatcher;

import javax.swing.*;
import java.awt.*;

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
        panel = new JPanel() {
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
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir componentes al panel
        matriculaLabel = new JLabel("Matrícula:");
        matriculaLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        matriculaLabel.setForeground(Color.WHITE);
        matriculaField = new JTextField();
        matriculaField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        matriculaField.setFont(new Font("Arial", Font.PLAIN, 14));
        matriculaField.setPreferredSize(new Dimension(200, 30));

        JButton checkButton = new RoundedButton("Check Matrícula", "#FFD700");
        checkButton.addActionListener(e -> checkMatricula());

        panel.add(matriculaLabel);
        panel.add(matriculaField);
        panel.add(new JLabel());
        panel.add(checkButton);

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

            // Limpiar panel
            panel.removeAll();

            // Agregar componentes para registro
            panel.setLayout(new GridLayout(6, 2, 10, 10));

            panel.add(matriculaLabel);
            panel.add(matriculaField);

            nombreLabel = new JLabel("Nombre:");
            nombreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            nombreLabel.setForeground(Color.WHITE);
            nombreField = new JTextField();
            nombreField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            nombreField.setFont(new Font("Arial", Font.PLAIN, 14));
            nombreField.setPreferredSize(new Dimension(200, 30));

            lastNameLabel = new JLabel("Apellido");
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

            panel.add(nombreLabel);
            panel.add(nombreField);
            panel.add(ciLabel);
            panel.add(ciField);

            JButton registerButton = new RoundedButton("Register", "#FF0000");
            registerButton.addActionListener(e -> register());
            panel.add(new JLabel());
            panel.add(registerButton);

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
