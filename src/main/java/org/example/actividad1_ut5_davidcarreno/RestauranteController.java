package org.example.actividad1_ut5_davidcarreno;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RestauranteController {
    @FXML
    private AnchorPane parent;
    @FXML
    private TableView<Producto> tablaTicket;
    @FXML
    private ImageView imagenModo;
    @FXML
    private VBox buttonBox, carritoBox;
    @FXML
    private GridPane resultadoGridPane;
    @FXML
    private TableColumn<Producto, String> columnaNombre;
    @FXML
    private TableColumn<Producto, Double> columnaPrecio;
    @FXML
    private TextField totalTF, impuestoTF;

    private ObservableList<Producto> listaProductos;
    private boolean isModoClaro = true;
    private Carrito carrito = new Carrito();

    @FXML
    public void initialize() {
        // Cargar estilos y datos iniciales
        parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        listaProductos = FXCollections.observableArrayList(
                new Producto("Hamburguesa", 3.0),
                new Producto("Cerveza", 2.5),
                new Producto("Gaseosa", 3.0),
                new Producto("Ensalada", 3.5),
                new Producto("Sandwich", 2.5),
                new Producto("Refresco", 2.0),
                new Producto("Perrito", 2.5),
                new Producto("Postre", 3.0)
        );

        // Configurar columnas de la tabla
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
        tablaTicket.setItems(listaProductos);

        iniciarListeners();
    }

    // Cambiar entre modo claro y oscuro
    public void cambiarModo() {
        isModoClaro = !isModoClaro;
        if (isModoClaro) {
            setModoClaro();
            aplicarModoClaro(resultadoGridPane);
            aplicarModoClaro(carritoBox);
        } else {
            setModoOscuro();
            aplicarModoOscuro(resultadoGridPane);
            aplicarModoOscuro(carritoBox);
        }
    }

    private void setModoClaro() {
        if (!parent.getStylesheets().contains(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm())) {
            parent.getStylesheets().remove(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/darkMode.css").toExternalForm());
            parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        }
        imagenModo.setImage(new Image(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/img/modo-claro.png").toExternalForm()));
    }

    private void setModoOscuro() {
        if (!parent.getStylesheets().contains(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/darkMode.css").toExternalForm())) {
            parent.getStylesheets().remove(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
            parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/darkMode.css").toExternalForm());
        }
        imagenModo.setImage(new Image(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/img/modo-oscuro.png").toExternalForm()));
    }

    private void aplicarModoClaro(Parent contenedor) {
        if (!contenedor.getStyleClass().contains("modo-claro")) {
            contenedor.getStyleClass().remove("modo-oscuro");
            contenedor.getStyleClass().add("modo-claro");
        }

        for (Node child : contenedor.getChildrenUnmodifiable()) {
            if (!child.getStyleClass().contains("modo-claro")) {
                child.getStyleClass().remove("modo-oscuro");
                child.getStyleClass().add("modo-claro");
            }
        }
    }

    private void aplicarModoOscuro(Parent contenedor) {
        if (!contenedor.getStyleClass().contains("modo-oscuro")) {
            contenedor.getStyleClass().remove("modo-claro");
            contenedor.getStyleClass().add("modo-oscuro");
        }

        for (Node child : contenedor.getChildrenUnmodifiable()) {
            if (!child.getStyleClass().contains("modo-oscuro")) {
                child.getStyleClass().remove("modo-claro");
                child.getStyleClass().add("modo-oscuro");
            }
        }
    }


    // agrega listeners a los botones de suma y resta
    @FXML
    private void iniciarListeners() {
        int i = 0;
        for (Node hijo : buttonBox.getChildrenUnmodifiable()) {
            Producto producto = listaProductos.get(i);
            if (hijo instanceof HBox) {
                Button botonSuma = (Button) ((HBox) hijo).getChildren().get(0);
                Button botonResta = (Button) ((HBox) hijo).getChildren().get(2);

                botonSuma.setOnAction(event -> aumentarCantidad(producto));
                botonResta.setOnAction(event -> disminuirCantidad(producto));
            }
            i++;
        }
    }

    private void actualizarCarritoVisual(Producto p) {
        carritoBox.getChildren().clear();

        // Itera por los productos en el carrito
        for (Producto producto : carrito.getCarrito().keySet()) {
            int cantidad = carrito.getCarrito().get(producto);

            // Crea un contenedor para cada producto del carrito
            HBox productoHBox = new HBox();

            // Añade la imagen del producto al HBox
            ImageView imagenProducto = new ImageView();
            imagenProducto.setImage(new Image(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/img/" + producto.getNombre() + ".png").toExternalForm()));
            // Le da tamaño
            imagenProducto.setFitWidth(50);
            imagenProducto.setFitHeight(50);

            // Crea etiquetas para el nombre y la cantidad
            Label etiquetaNombre = new Label(producto.getNombre());
            Label etiquetaCantidad = new Label("x" + cantidad);
            etiquetaNombre.getStyleClass().add("texto");
            etiquetaCantidad.getStyleClass().add("texto");

            // Agregar los nodos al HBox
            productoHBox.getChildren().addAll(imagenProducto, etiquetaNombre, etiquetaCantidad);

            productoHBox.getStyleClass().add("item-carrito");

            // Agregar el HBox al VBox principal
            carritoBox.getChildren().add(productoHBox);
        }

        // Actualizar los totales
        totalTF.setText(String.format("%.2f€", carrito.getTotal()));
        impuestoTF.setText(String.format("%.2f€", carrito.getTotal() - (carrito.getTotal() / 1.07 ))); // 7% de IGIC
    }

    private void aumentarCantidad(Producto p) {
        // añade el producto al carrito
        carrito.anyadirProducto(p);
        // Actualiza el componente carrito para mostrarlo con el nuevo producto
        actualizarCarritoVisual(p);
    }

    private void disminuirCantidad(Producto p) {
        // Quita el producto del carrito
        carrito.removeProducto(p);
        actualizarCarritoVisual(p);
    }

    @FXML
    private void limpiar() {
        // Limpiar el VBox que contiene el carrito
        carritoBox.getChildren().clear();
        // Crea nuevo carrito
        carrito = new Carrito();
    }

    @FXML
    private void cerrar() {
        // Sale de la aplicacion
        System.exit(0);
    }

    @FXML
    private synchronized void aceptar() {
        //Crea la factura en un hilo a parte.
        //Platform.runLater hace que el hilo principal de la interfaz no colapse si se ejcuta otro hilo que modifica la interfaz
        Platform.runLater(() -> {
            try {
                Factura factura = new Factura(carrito);
                factura.generarFactura("FacturaDePrueba.pdf");
                factura.abrirFactura();
                limpiar();
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.exit(0);
    }
}
