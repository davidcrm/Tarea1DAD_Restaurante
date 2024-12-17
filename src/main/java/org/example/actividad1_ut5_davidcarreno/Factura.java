package org.example.actividad1_ut5_davidcarreno;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.text.BaseColor;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;



public class Factura {
    private final Carrito carrito;

    public Factura(Carrito carrito) {
        this.carrito = carrito;
    }

    public void generarFactura(String archivoSalida) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/Factura/"+ archivoSalida));
        document.open();

        // Fuente personalizada
        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
        Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        // Logo
        String rutaLogo = "/org/example/actividad1_ut5_davidcarreno/img/logo.png";
        // Encabezado
        Paragraph encabezado = new Paragraph("Factura de Compra", tituloFont);
        encabezado.setAlignment(Element.ALIGN_CENTER);
        document.add(encabezado);
        /*
        ImageData imageData = ImageDataFactory.create(Objects.requireNonNull(getClass().getResource(rutaLogo)));
        Image imagen = new Image(imageData);
        imagen.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        document.add((Element) imagen.setAutoScale(true));
        */
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Restaurante Lomo De La Herradura.", subtituloFont));
        document.add(new Paragraph("Fecha: " + LocalDate.now(), textoFont));
        document.add(new Paragraph(" "));

        // Tabla de productos
        PdfPTable tabla = new PdfPTable(4); // 4 columnas
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{4, 1, 1, 1}); // Configuración de ancho por columna

        // Encabezados de tabla
        agregarCeldaEncabezado(tabla, "Producto");
        agregarCeldaEncabezado(tabla, "Cantidad");
        agregarCeldaEncabezado(tabla, "Precio Unitario");
        agregarCeldaEncabezado(tabla, "Subtotal");

        // Datos de productos
        for (Map.Entry<Producto, Integer> entry : carrito.getCarrito().entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            double subtotal = producto.getPrecio() * cantidad;

            agregarCelda(tabla, producto.getNombre(), textoFont);
            agregarCelda(tabla, String.valueOf(cantidad), textoFont);
            agregarCelda(tabla, String.format("%.2f", producto.getPrecio()) + " €", textoFont);
            agregarCelda(tabla, String.format("%.2f", subtotal)+ " €", textoFont);
        }

        // Añadir tabla al documento
        document.add(tabla);

        // Total sin impuesto
        Paragraph totalSinImpuesto = new Paragraph(
                "Total sin impuesto: " +
                        String.format("%.2f", (carrito.getTotal() / 1.07)) + " €",
                        tituloFont
        );
        totalSinImpuesto.setAlignment(Element.ALIGN_RIGHT);
        document.add(new Paragraph(" "));
        document.add(totalSinImpuesto);

        hr(document);

        // impuesto
        Paragraph impuesto = new Paragraph(
                "Impuesto: " +
                        String.format("%.2f", (carrito.getTotal() * 0.07)) + " €",
                        tituloFont
        );
        impuesto.setAlignment(Element.ALIGN_RIGHT);
        document.add(new Paragraph(" "));
        document.add(impuesto);

        hr(document);

        // Total con impuesto
        Paragraph total = new Paragraph(
                "Total: " +
                        String.format("%.2f", carrito.getTotal()) + " €",
                        tituloFont
        );
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(new Paragraph(" "));
        document.add(total);

        hr(document);

        // Pie de página
        Paragraph footer = new Paragraph("¡Gracias por su compra!", subtituloFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(" "));
        document.add(footer);

        document.close();
    }

    private void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto));
        celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }

    private void agregarCelda(PdfPTable tabla, String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }
    private void hr(Document document){
        try {
            LineSeparator separador = new LineSeparator();
            separador.setLineWidth(1f);
            separador.setLineColor(BaseColor.BLACK);

            Chunk chunk = new Chunk(separador);

            Paragraph paragraph = new Paragraph(chunk);
            paragraph.setSpacingBefore(5);

            document.add(paragraph);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void abrirFactura() {

            try {
                String rutaPdf = "src/main/resources/Factura/FacturaDePrueba.pdf";

                File pdf = new File(rutaPdf);

                if (pdf.exists()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(pdf);
                } else {
                    System.out.println("El archivo no existe: " + rutaPdf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
