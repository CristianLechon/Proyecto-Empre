package ec.edu.uce.view;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    public MainUI() {
        setTitle("Aneta");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Panel principal con fondo degradado
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = Color.decode("#FFD700"); // Amarillo
                Color color2 = Color.decode("#000080"); // Azul
                GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Añadir el logo en la parte superior
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/main/resources/imagenes/anetaLog.png");
        logoLabel.setIcon(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(logoLabel, BorderLayout.NORTH);

        // Panel para los botones e imágenes
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        buttonPanel.setOpaque(false);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Panel individual para Despachador
        JPanel dispatcherPanel = new JPanel(new BorderLayout());
        dispatcherPanel.setOpaque(false);
        JLabel dispatcherImageLabel = new JLabel();
        ImageIcon dispatcherIcon = new ImageIcon("src/main/resources/imagenes/despachador.png");
        dispatcherImageLabel.setIcon(dispatcherIcon);
        dispatcherImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton dispatcherButton = new RoundedButton("Despachador", "#FFD700");
        dispatcherButton.addActionListener(e -> new DispatcherLoginFrame().setVisible(true));

        dispatcherPanel.add(dispatcherImageLabel, BorderLayout.CENTER);
        dispatcherPanel.add(dispatcherButton, BorderLayout.SOUTH);

        // Panel individual para Administrador
        JPanel adminPanel = new JPanel(new BorderLayout());
        adminPanel.setOpaque(false);
        JLabel adminImageLabel = new JLabel();
        ImageIcon adminIcon = new ImageIcon("src/main/resources/imagenes/admin.png");
        adminImageLabel.setIcon(adminIcon);
        adminImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton adminButton = new RoundedButton("Administrador", "#000080");
        adminButton.addActionListener(e -> new AdminLoginFrame().setVisible(true));

        adminPanel.add(adminImageLabel, BorderLayout.CENTER);
        adminPanel.add(adminButton, BorderLayout.SOUTH);

        // Añadir los paneles individuales al panel principal de botones
        buttonPanel.add(dispatcherPanel);
        buttonPanel.add(adminPanel);

        getContentPane().setBackground(Color.WHITE);
        add(panel);

        setVisible(true);
    }

    private static class RoundedButton extends JButton {
        public RoundedButton(String text, String colorHex) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setForeground(Color.BLACK); // Black text color
            setBackground(Color.decode(colorHex));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setPreferredSize(new Dimension(150, 40));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            super.paintComponent(g);
            g2d.dispose();
        }
    }

    public void display() {
        setVisible(true);
    }
}