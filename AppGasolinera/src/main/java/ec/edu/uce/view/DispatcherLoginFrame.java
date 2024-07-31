package ec.edu.uce.view;

import ec.edu.uce.controller.Container;

import javax.swing.*;
import java.awt.*;

public class DispatcherLoginFrame extends JFrame {

    private JTextField usernameField, lastNameField;
    private JPasswordField passwordField;
    private Container container;
    private boolean isRegisterMode;
    private JLabel lastNameLabel;
    private JButton loginButton, registerButton, toggleButton;

    public DispatcherLoginFrame() {
        setTitle("Login - Dispatcher");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        container = new Container();
        isRegisterMode = false;

        JPanel panel = new JPanel() {
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
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/resources/imagenes/anetaLog.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(logoLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lastNameLabel.setForeground(Color.WHITE);
        lastNameField = new JTextField();
        lastNameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        lastNameField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(lastNameLabel);
        lastNameLabel.setVisible(false);
        inputPanel.add(lastNameField);
        lastNameField.setVisible(false);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        loginButton = new RoundedButton("Login", "#4CAF50"); // Verde suave
        registerButton = new RoundedButton("Register", "#FF5252"); // Rojo suave
        toggleButton = new RoundedButton("Switch to Register", "#2196F3"); // Azul claro

        loginButton.addActionListener(e -> {
            if (!isRegisterMode) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                container.loginDispatcher(username, password);
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
                container.registerDispatcher(username, lastName, password);
            }
        });

        toggleButton.addActionListener(e -> setRegisterMode(!isRegisterMode));

        buttonPanel.add(loginButton);
        registerButton.setVisible(false);
        buttonPanel.add(registerButton);
        buttonPanel.add(toggleButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        add(panel);

        setVisible(true);
    }

    private void setRegisterMode(boolean registerMode) {
        this.isRegisterMode = registerMode;
        lastNameField.setVisible(registerMode);
        lastNameLabel.setVisible(registerMode);
        registerButton.setVisible(registerMode);
        loginButton.setVisible(!registerMode);
        toggleButton.setText(registerMode ? "Switch to Login" : "Switch to Register");
        ((JPanel) getContentPane().getComponent(0)).revalidate();
        ((JPanel) getContentPane().getComponent(0)).repaint();
    }

    static class RoundedButton extends JButton {
        public RoundedButton(String text, String colorHex) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(Color.decode(colorHex));
            setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Borde interno para un diseño más plano
            setFont(new Font("SansSerif", Font.BOLD, 14)); // Cambio de fuente
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