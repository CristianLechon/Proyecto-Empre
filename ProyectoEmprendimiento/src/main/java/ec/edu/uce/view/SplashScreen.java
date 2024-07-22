package ec.edu.uce.view;

import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.callback.TimelineCallback;
import org.pushingpixels.trident.ease.Sine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Panel de contenido con fondo de degradado y bordes curvos
        JPanel content = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Definir los colores del degradado
                Color color1 = Color.decode("#FFD700"); // Amarillo
                Color color2 = Color.decode("#000080"); // Azul
                Color color3 = Color.decode("#EF1B23"); // Rojo

                // Crear el degradado con 3 colores
                float[] fractions = {0.0f, 0.5f, 1.0f};
                Color[] colors = {color1, color2, color3};
                LinearGradientPaint gradient = new LinearGradientPaint(0, 0, getWidth(), getHeight(), fractions, colors);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        content.setOpaque(false);

        JLabel label = new JLabel(new ImageIcon("src/main/resources/imagenes/anetaLog.png"));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(label, BorderLayout.CENTER);

        JLabel loadingLabel = new JLabel("Cargando...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        loadingLabel.setForeground(Color.DARK_GRAY);
        content.add(loadingLabel, BorderLayout.SOUTH);

        // Usar un panel transparente para redondear la ventana
        JPanel roundedPanel = new JPanel(new BorderLayout()) {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        roundedPanel.setOpaque(false);
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.add(content, BorderLayout.CENTER);

        add(roundedPanel);

        // Timeline for fading effect on the SplashScreen
        Timeline timeline = new Timeline(this);
        timeline.addPropertyToInterpolate("opacity", 0.0f, 1.0f);
        timeline.setDuration(2000);
        timeline.setEase(new Sine());
        timeline.play();

        // Timeline for animating the "Cargando..." text
        Timeline textTimeline = new Timeline(loadingLabel);
        textTimeline.addPropertyToInterpolate("foreground", Color.DARK_GRAY, Color.LIGHT_GRAY);
        textTimeline.setDuration(500);
        textTimeline.setEase(new Sine());
        textTimeline.playLoop(Timeline.RepeatBehavior.REVERSE);

        Timer timer = new Timer(3000, e -> { // Esperar 3 segundos antes de comenzar el fade out
            Timeline fadeOut = new Timeline(this);
            fadeOut.addPropertyToInterpolate("opacity", 1.0f, 0.89f);
            fadeOut.setDuration(1000);
            fadeOut.addCallback(new TimelineCallback() {
                @Override
                public void onTimelineStateChanged(Timeline.TimelineState oldState, Timeline.TimelineState newState, float durationFraction, float timelinePosition) {
                    if (newState == Timeline.TimelineState.DONE) {
                        dispose();
                        SwingUtilities.invokeLater(() -> new MainUI().display());
                    }
                }

                @Override
                public void onTimelinePulse(float durationFraction, float timelinePosition) {
                    // No-op
                }
            });
            fadeOut.play();
        });
        timer.setRepeats(false);
        timer.start();

        // Asegúrate de que el JFrame sea redondeado
        setOpacity(0.0f);
        setVisible(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
    }
}
