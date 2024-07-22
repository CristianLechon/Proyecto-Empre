package ec.edu.uce.util;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import ec.edu.uce.controller.DispatcherContext;
import ec.edu.uce.enums.TipoGasolina;
import ec.edu.uce.model.Cliente;
import ec.edu.uce.model.GasolineraManager;

import java.io.*;
import java.util.Date;
import java.util.Random;

public class FacturaPDF {

    private static final String FACTURA_ID_FILE = "src/main/resources/factura_id.txt";

    public static void generarFactura(Cliente cliente, GasolineraManager gasolinera, TipoGasolina tipoGasolina, float precio, float galones, float total, float iva, float total_iva, String tipoPago) {
        int facturaId = obtenerYIncrementarFacturaId();

        String escritorio = System.getProperty("user.home") + File.separator + "Desktop";
        File directorioFactura = new File(escritorio);

        if (!directorioFactura.exists()) {
            directorioFactura.mkdir();
        }

        String archivoFactura = escritorio + File.separator + facturaId + "_factura.pdf";

        try {
            PdfWriter writer = new PdfWriter(archivoFactura);
            PdfDocument pdfDoc = new PdfDocument(writer);
            PageSize pageSizeA6 = new PageSize(105 * 72 / 25.4f, 148 * 72 / 25.4f);
            Document document = new Document(pdfDoc, pageSizeA6);
            document.setMargins(10, 10, 10, 10);

            document.add(new Paragraph("ANETA")
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold());
            document.add(new Paragraph("RUC: 0991331859001")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("AV. DE LAS AMERICAS 406-GUAYAQUIL")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Contribuyente Especial")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Resolucion Nro. 01477 - 12/12/2008")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Sucursal: PANAMERICANA NORTE KM 3 1/2 VIA IBARRA")
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Cliente: " + cliente.getNombre())
                    .setFontSize(8));
            document.add(new Paragraph("CEDULA: " + cliente.getCI())
                    .setFontSize(8));
            document.add(new Paragraph("Fecha: " + new Date())
                    .setFontSize(8));
            document.add(new Paragraph("Factura ID: " + facturaId)
                    .setFontSize(8));
            Random random = new Random();
            int turno = random.nextInt(20) + 1;
            document.add(new Paragraph("Cajero: " + DispatcherContext.getCurrentDispatcher().getUsername() + " Turno: " + turno)
                    .setFontSize(8));

            document.add(new Paragraph("---------------------------------------------------------------"));

            float[] columnWidths = {3, 1, 1};
            Table table = new Table(columnWidths);
            table.addCell(new Paragraph("Producto").setFontSize(8).setBold());
            table.addCell(new Paragraph("Cantidad").setFontSize(8).setBold());
            table.addCell(new Paragraph("Precio").setFontSize(8).setBold());

            table.addCell(new Paragraph(tipoGasolina.getNombre()).setFontSize(8));
            table.addCell(new Paragraph(String.valueOf(galones)).setFontSize(8));
            table.addCell(new Paragraph(String.valueOf(total)).setFontSize(8));

            table.addCell(new Paragraph("*** Valor Venta 0% US$").setFontSize(8));
            table.addCell(new Paragraph("0.00").setFontSize(8));
            table.addCell(new Paragraph("0.00").setFontSize(8));

            table.addCell(new Paragraph("*** Valor Venta 15% US$").setFontSize(8));
            table.addCell(new Paragraph(String.valueOf(total)).setFontSize(8));
            table.addCell(new Paragraph(String.valueOf(total)).setFontSize(8));

            table.addCell(new Paragraph("*** I.V.A. 15% US$").setFontSize(8));
            table.addCell(new Paragraph(String.valueOf(iva)).setFontSize(8));
            table.addCell(new Paragraph(String.valueOf(iva)).setFontSize(8));

            table.addCell(new Paragraph("*** TOTAL A PAGAR US$").setFontSize(8).setBold());
            table.addCell(new Paragraph(String.valueOf(total_iva)).setFontSize(8).setBold());
            table.addCell(new Paragraph(String.valueOf(total_iva)).setFontSize(8).setBold());
            document.add(table);

            document.add(new Paragraph("---------------------------------------------------------------"));

            document.add(new Paragraph("Forma de Pago: US$ " + tipoPago)
                    .setFontSize(8));
            document.add(new Paragraph("SIN UTILIZACION DEL SIST 0 DIAS PLAZO")
                    .setFontSize(8));

            document.add(new Paragraph("INFORMATIVO:")
                    .setFontSize(8).setBold());
            document.add(new Paragraph("Precio sin Subsidio x G1 US$ " + gasolinera.obtenerPrecio(tipoGasolina))
                    .setFontSize(8));
            document.add(new Paragraph("Valor Total sin Subsidio US$ " + (gasolinera.obtenerPrecio(tipoGasolina) + total))
                    .setFontSize(8));
            document.add(new Paragraph("Ahorro por Subsidio US$ 6.96")
                    .setFontSize(8));

            document.add(new Paragraph("Lado: 06 Placa: " + cliente.getMatricula())
                    .setFontSize(8).setBold());
            document.add(new Paragraph("AGRADECEMOS TU COMPRA")
                    .setFontSize(8).setTextAlignment(TextAlignment.CENTER).setBold());

            document.add(new Paragraph("Descarga tu comprobante autorizado desde el portal www.aneta.com.ec")
                    .setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Ingresa con tu usuario: RUC o Cedula")
                    .setFontSize(8).setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Remitir sus comprobantes de retencion a relectrónicas@aneta.com.ec")
                    .setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("ANETA CADA GOTA CUENTA")
                    .setFontSize(8).setTextAlignment(TextAlignment.CENTER).setBold());

            document.add(new Paragraph("Estimado Cliente, para cualquier novedad y/o inconveniente con su factura escríbanos a contactanos@aneta.com.ec")
                    .setFontSize(8).setTextAlignment(TextAlignment.CENTER));
            document.close();
            System.out.println("Factura generada exitosamente en: " + archivoFactura);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int obtenerYIncrementarFacturaId() {
        int facturaId = 1; // Valor inicial predeterminado
        File archivoId = new File(FACTURA_ID_FILE);

        // Leer el último ID desde el archivo
        if (archivoId.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoId))) {
                String lastIdStr = null;
                String line;
                while ((line = br.readLine()) != null) {
                    lastIdStr = line;
                }
                if (lastIdStr != null) {
                    facturaId = Integer.parseInt(lastIdStr.trim()) + 1;
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Agregar el nuevo ID al archivo
        try (FileWriter fw = new FileWriter(archivoId, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(facturaId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return facturaId;
    }

}