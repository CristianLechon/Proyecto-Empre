package ec.edu.uce.view;

import ec.edu.uce.controller.Container;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminMetod extends JFrame {
    private Container container;

    public AdminMetod() throws HeadlessException {
        container = new Container();
        setTitle("Administración Contable");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Botón para generar reporte
        JButton generateReport = new JButton("Generar Reporte");
        generateReport.addActionListener(e -> generateAccountingReport());

        // Añadir botón al frame
        add(generateReport);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    // Método para generar un reporte contable
    private void generateAccountingReport() {
        // Simular algunos datos contables
        String reportContent = "Reporte Contable\n";
        reportContent += "Fecha y Hora: " + getCurrentDateTime() + "\n";
        reportContent += "-------------------------\n";
        reportContent += "Total Ventas: $5000\n";
        reportContent += "Total Gastos: $2000\n";
        reportContent += "Ganancia Neta: $3000\n";

        // Guardar el reporte en un archivo
        saveReportToFile(reportContent);

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(this, "Reporte generado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    // Método para obtener la fecha y hora actual
    private String getCurrentDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    // Método para guardar el reporte en un archivo
    private void saveReportToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/reporte_contable.txt"))) {
            writer.write(content);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el reporte.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
