package ec.edu.uce.view;

import ec.edu.uce.controller.Container;
import ec.edu.uce.model.Dispatcher;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {
    private JTextField usernameField, lastNameField;
    private JPasswordField passwordField;
    private Container container;
    private boolean isRegisterMode;
    private JLabel lastNameLabel;
    private JButton loginButton, registerButton, toggleButton;

    public AdminLoginFrame() {
        setTitle("Login - Admin");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        container = new Container();
        isRegisterMode = false;

        // Panel principal con fondo degradado
        JPanel panel = new JPanel() {
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
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir el logo
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/resources/imagenes/anetaLog.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(logoLabel, BorderLayout.NORTH);

        // Panel para los campos de texto y etiquetas
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));

        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lastNameLabel.setForeground(Color.WHITE);
        lastNameField = new JTextField();
        lastNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        lastNameField.setPreferredSize(new Dimension(200, 30));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(lastNameLabel);
        lastNameLabel.setVisible(false);
        inputPanel.add(lastNameField);
        lastNameField.setVisible(false);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        loginButton = new RoundedButton("Login", "#FFD700"); // Amarillo
        registerButton = new RoundedButton("Register", "#FF0000"); // Rojo
        toggleButton = new DispatcherLoginFrame.RoundedButton("Switch to Register", "#00FF00"); // Verde


        loginButton.addActionListener(e -> {
                    if (isRegisterMode) {
                        setRegisterMode(false);
                    } else {
                        String username = usernameField.getText().trim();
                        String password = new String(passwordField.getPassword());
                        container.loginAdmin(username, password);
                        dispose();
                    }
        });
        registerButton.addActionListener(e -> {
            if (isRegisterMode) {
                String username = usernameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor complete todos los campos.");
                    return;
                }
                container.registerAdmin(username,lastName,password);
            }
        });

        toggleButton.addActionListener(e -> setRegisterMode(!isRegisterMode));

        buttonPanel.add(loginButton);
        registerButton.setVisible(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(toggleButton);

        // Añadir paneles al panel principal
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Fondo y diseño del JFrame
        getContentPane().setBackground(Color.WHITE);
        add(panel);

        setVisible(true);
    }

    private void setRegisterMode(boolean registerMode) {
        this.isRegisterMode = registerMode;
        lastNameField.setVisible(registerMode);
        lastNameLabel.setVisible(registerMode);
        registerButton.setVisible(registerMode); // Show Register button only in register mode
        loginButton.setVisible(!registerMode); // Hide Login button in register mode

        // Update toggle button text
        toggleButton.setText(registerMode ? "Switch to Login" : "Switch to Register");

        // Actualizar las etiquetas y campos en el panel de entrada
        ((JPanel) getContentPane().getComponent(0)).revalidate();
        ((JPanel) getContentPane().getComponent(0)).repaint();
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
