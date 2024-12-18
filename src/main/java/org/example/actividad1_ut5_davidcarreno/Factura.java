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
import javafx.application.Platform;


public class Factura {
    private final Carrito carrito;
    private String archivoSalida;
    private final Document document = new Document();
    // Constructor
    public Factura(Carrito carrito, String archivoSalida) {
        this.carrito = carrito;
        this.archivoSalida = archivoSalida;
    }

    // Metodo para generar la factura
    public void generarFactura() throws DocumentException, IOException {

        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/Factura/"+ archivoSalida));
        document.open();

        // Crea una fuente personalizada
        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
        Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.DARK_GRAY);
        Font textoFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        // Logo
        // String rutaLogo = "/org/example/actividad1_ut5_davidcarreno/img/logo.png";

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
        // Añade elementos al pdf
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Restaurante Lomo De La Herradura.", subtituloFont));
        document.add(new Paragraph("Fecha: " + LocalDate.now(), textoFont));
        document.add(new Paragraph(" "));

        // Crea la tabla de productos
        PdfPTable tabla = new PdfPTable(4); // Establece 4 columnas
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{4, 1, 1, 1}); // Configura el ancho por columna

        // Crea los encabezados de la tabla
        agregarCeldaEncabezado(tabla, "Producto");
        agregarCeldaEncabezado(tabla, "Cantidad");
        agregarCeldaEncabezado(tabla, "Precio Unitario");
        agregarCeldaEncabezado(tabla, "Subtotal");

        // Añade los datos de los productos a la tabla
        for (Map.Entry<Producto, Integer> entry : carrito.getCarrito().entrySet()) {
            // Obtiene el ID del objeto
            Producto producto = entry.getKey();
            // Obtiene la cantidad
            int cantidad = entry.getValue();
            // calcula el subtotal por producto
            double subtotal = producto.getPrecio() * cantidad;
            // Agregamos a la tabla las celdas
            agregarCelda(tabla, producto.getNombre(), textoFont);
            agregarCelda(tabla, String.valueOf(cantidad), textoFont);
            agregarCelda(tabla, String.format("%.2f", producto.getPrecio()) + " €", textoFont);
            agregarCelda(tabla, String.format("%.2f", subtotal)+ " €", textoFont);
        }

        // Añadir tabla al documento
        document.add(tabla);

        // Añade un párrafo para el Total sin impuesto
        Paragraph totalSinImpuesto = new Paragraph(
                "Total sin impuesto: " +
                        String.format("%.2f", (carrito.getTotal() / 1.07)) + " €",
                        tituloFont
        );
        totalSinImpuesto.setAlignment(Element.ALIGN_RIGHT);
        document.add(new Paragraph(" "));
        document.add(totalSinImpuesto);

        hr(document);

        // Añade un párrafo para el Impuesto
        Paragraph impuesto = new Paragraph(
                "Impuesto: " +
                        String.format("%.2f", (carrito.getTotal() * 0.07)) + " €",
                        tituloFont
        );
        impuesto.setAlignment(Element.ALIGN_RIGHT);
        document.add(new Paragraph(" "));
        document.add(impuesto);

        hr(document);

        // Añade un párrafo para el Impuesto
        Paragraph total = new Paragraph(
                "Total: " +
                        String.format("%.2f", carrito.getTotal()) + " €",
                        tituloFont
        );
        total.setAlignment(Element.ALIGN_RIGHT);
        document.add(new Paragraph(" "));
        document.add(total);

        hr(document);

        // Añade un párrafo para el Pie de página
        Paragraph footer = new Paragraph("¡Gracias por su compra!", subtituloFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(" "));
        document.add(footer);

        document.close();
    }

    // Metodo para agregar un título a la tabla
    private void agregarCeldaEncabezado(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto));
        celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }

    // Metodo para agregar una celda a la tabla
    private void agregarCelda(PdfPTable tabla, String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(celda);
    }
    // Metodo para añadir una linea vertical al documento
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

    // Metodo sincronizado para abrir la factura generada en un hilo
    public synchronized void abrirFactura() {
       new Thread(() -> {
            try {
                String rutaPdf = "src/main/resources/Factura/" + archivoSalida;
                File pdf = new File(rutaPdf);

                // Solo sirve para abrir el pdf en Wndows y mac que tienen visor de archivos por defecto
                Desktop desktop = Desktop.getDesktop();
                desktop.open(pdf);

                // Para Ubuntu creamos un processBuilder que lo abra desde la consola (clase ProcessBuilder vista en PGV)
                //new ProcessBuilder("xdg-open", pdf.getAbsolutePath()).start();

                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
       }).start();
    }
}
