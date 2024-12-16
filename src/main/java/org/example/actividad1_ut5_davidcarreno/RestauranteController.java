package org.example.actividad1_ut5_davidcarreno;


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

import java.util.List;

public class RestauranteController {
    @FXML
    private AnchorPane parent;
    @FXML
    private TableView tablaTicket;
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
        // Añade los valores a las columnas correspondientes
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaPrecio.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());

        tablaTicket.setItems(listaProductos);
        iniciarListeners();
    }
    // Metodo para cambiar el modo claro u oscuro
    public void cambiarModo(){
        isModoClaro = !isModoClaro;
        if (isModoClaro){
            setModoClaro();
            aplicarModoClaro(resultadoGridPane);
            aplicarModoClaro(carritoBox);
        }
        else {
            setModoOscuro();
            aplicarModoOscuro(resultadoGridPane);
            aplicarModoOscuro(carritoBox);
        }
    }
    //Metodos para cambiar los archivos de referencia, tanto de estilos como imágenes usando el classpath
    private void setModoClaro() {
        parent.getStylesheets().remove(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/darkMode.css").toExternalForm());
        parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        Image image = new Image(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/img/modo-claro.png").toExternalForm());
        imagenModo.setImage(image);
    }

    private void setModoOscuro() {
        parent.getStylesheets().remove(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/darkMode.css").toExternalForm());
        Image image = new Image(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/img/modo-oscuro.png").toExternalForm());
        imagenModo.setImage(image);
    }
    // hacen lo mismo que los anteriores pero con un componente entero
    private void aplicarModoClaro(Parent contenedor) {
        // Aplicar estilo al VBox
        contenedor.getStyleClass().remove("modo-oscuro");
        contenedor.getStyleClass().add("modo-claro");

        // Aplicar estilo a los hijos del VBox
        for (Node child : contenedor.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("modo-oscuro");
            child.getStyleClass().add("modo-claro");
        }
    }

    private void aplicarModoOscuro(Parent contenedor) {
        // Aplicar estilo al VBox
        contenedor.getStyleClass().remove("modo-claro");
        contenedor.getStyleClass().add("modo-oscuro");

        // Aplicar estilo a los hijos del VBox
        for (Node child : contenedor.getChildrenUnmodifiable()) {
            child.getStyleClass().remove("modo-claro");
            child.getStyleClass().add("modo-oscuro");
        }
    }

    @FXML
    // Metodo para calcular el total de la venta y el impuesto
    private double calcularTotal() {
        double total = 0.0;
        double impuesto = 0.0;



        // Una vez iterados todos los textfields se muestra el total del pedido
        totalTF.setText(String.format("%.2f", total) + "€");
        // y el impuesto correspodiente
        impuestoTF.setText(String.format("%.2f", impuesto) + "€");
        return total;
    }

    // Metodo para obtener el precio de un item
    private double obtenerPrecio(String nombreProducto){
        // Devuelve una lista con los productos
        List listaOrdenada = listaProductos.stream().toList();
        // Recorremos la lista y guardamos en variables el objeto y el nombre
        for (int i = 0; i < listaOrdenada.size(); i++) {
            Producto producto = (Producto) listaOrdenada.get(i);
            String nombre = producto.getNombre();
            // Comprobamos que el nombre del objeto iterado es igual al que pasamos por parametro
            if ((nombreProducto.substring(0, 1).toUpperCase() + nombreProducto.substring(1).toLowerCase()).equals(nombre)){
                Producto productoFinal = (Producto) listaOrdenada.get(i);
                // Devolvemos el precio de ese producto
                return productoFinal.getCantidad();
            }
        }
        // En caso de no encontrarlo, devuelve 0.0
        return 0.0;
    }
    @FXML
    private void iniciarListeners(){
        int i = 0;
       for (Node hijo : buttonBox.getChildrenUnmodifiable()){
           Producto producto = listaProductos.get(i);
           if (hijo instanceof HBox){
               // Recupera el primer boton (suma)
               Button botonSuma = (Button) ((HBox) hijo).getChildren().get(0);
               // Recupera el segundo boton (resta)
               Button botonResta = (Button) ((HBox) hijo).getChildren().get(2);
                // Añade los eventos a cada botón
               botonSuma.setOnAction(event -> aumentarCantidad(producto));
               botonResta.setOnAction(event -> disminuirCantidad(producto));
           }
           i++;
       }
    }

    private void actualizarCarrito(Producto p){
        ImageView imagenProducto = new ImageView(new Image(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/img/" + p.getNombre() + ".png").toExternalForm()));
        imagenProducto.setFitWidth(40);
        imagenProducto.setFitHeight(40);
        Node nombreProducto = (Node) new Label(p.getNombre());
        Label cantidadProducto = new Label( "x" + p.getCantidadEnCarrito());
        HBox itemCarrito = new HBox();

        if (carrito.contains(p)){
            p.aumentarCantidadProducto();
            carrito.anyadirProducto(p);
            totalTF.setText(String.valueOf(carrito.getTotal()));
            impuestoTF.setText(String.format("%.2f", (carrito.getTotal() * 0.07)) + "€");
        } else {
            itemCarrito.getChildren().addAll(imagenProducto,nombreProducto,cantidadProducto);
            itemCarrito.getStyleClass().addLast("item");
            carritoBox.getChildren().addLast(itemCarrito);
            carrito.anyadirProducto(p);
            totalTF.setText(String.valueOf(carrito.getTotal()));
            impuestoTF.setText(String.format("%.2f", (carrito.getTotal() * 0.07)) + "€");

            p.cantidadEnCarritoProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() == 0) {
                    p.eliminarDelCarrito();
                    p.eliminarDelCarrito();
                    carritoBox.getChildren().remove(itemCarrito);
                    carrito.removeProducto(p);
                    carrito.setTotal(carrito.getTotal() - p.getSubtotal());
                } else {
                    cantidadProducto.setText("x" + newValue);
                }});
        carrito.listarCarrito();
        System.out.println(carrito.getTotal());
        }


    }

    private void aumentarCantidad(Producto p){
        p.aumentarCantidadProducto();
        System.out.println(p.getCantidadEnCarrito());
        System.out.println(p.getSubtotal());
        actualizarCarrito(p);
    }
    private void disminuirCantidad(Producto p){
        p.eliminarDelCarrito();
        System.out.println(p.getCantidadEnCarrito());

    }

    @FXML
    private void limpiar(){

    }
    @FXML
    private void cerrar(){
        // Cierra el  programa
        System.exit(0);
    }

    @FXML
    private void aceptar(){
        // Muestra un dialogo con el total del pedido
        Utils.mostrarDialogo("HA COMPLETADO SU PEDIDO.","El total de su pedido es de: " + calcularTotal() + " €");
        // Y cierra el programa
        System.exit(0);
    }

}