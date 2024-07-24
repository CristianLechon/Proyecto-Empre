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
                // Definir los colores del degradado
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

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));

        JButton dispatcherButton = new RoundedButton("Despachador", "#FFD700"); // Amarillo
        JButton adminButton = new RoundedButton("Administrador", "#000080"); // Azul

        dispatcherButton.addActionListener(e -> new DispatcherLoginFrame().setVisible(true));
        adminButton.addActionListener(e -> new AdminLoginFrame().setVisible(true));

        buttonPanel.add(dispatcherButton);
        buttonPanel.add(adminButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        // Fondo y diseño del JFrame
        getContentPane().setBackground(Color.WHITE);
        add(panel);

        setVisible(true);
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
            setPreferredSize(new Dimension(150, 50));
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

    public void display() {
        setVisible(true);
    }
}
