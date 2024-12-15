package org.example.actividad1_ut5_davidcarreno;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private VBox labelBox,textFBox;
    @FXML
    private GridPane resultadoGridPane;
    @FXML
    private TableColumn<Producto, String> columnaNombre;
    @FXML
    private TableColumn<Producto, Double> columnaPrecio;
    @FXML
    private ObservableList<Producto> listaProductos;
    @FXML
    private TextField totalTF, impuestoTF;
    private boolean isModoClaro = true;
    @FXML
    public void initialize() {
        parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        listaProductos = FXCollections.observableArrayList(
                new Producto("Gaseosa", 3.0),
                new Producto("Refresco", 2.0),
                new Producto("Cerveza", 2.5),
                new Producto("Ensalada", 3.5),
                new Producto("Hamburguesa", 3.0),
                new Producto("Perrito", 2.5),
                new Producto("Sandwich", 2.5),
                new Producto("Postre", 3.0)

        );
        // Añade los valores a las columnas correspondientes
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaPrecio.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());

        tablaTicket.setItems(listaProductos);

        inicializarListeners();
    }
    // Metodo para cambiar el modo claro u oscuro
    public void cambiarModo(ActionEvent event){
        isModoClaro = !isModoClaro;
        if (isModoClaro){
            setModoClaro();
            aplicarModoClaro(labelBox);
            aplicarModoClaro(textFBox);
            aplicarModoClaro(resultadoGridPane);
        }
        else {
            setModoOscuro();
            aplicarModoOscuro(labelBox);
            aplicarModoOscuro(textFBox);
            aplicarModoOscuro(resultadoGridPane);
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
    // Método para calcular el total de la venta y el impuesto
    private double calcularTotal() {
        double total = 0.0;
        double impuesto = 0.0;
        for (Node child : textFBox.getChildrenUnmodifiable()) {
            // Verifica que el Nodo sea un TextField
            if (child instanceof TextField) {
                TextField tf = (TextField) child;
                // Ignorar TextFields vacíos o nulos
                String texto = tf.getText();
                if (texto != null && !texto.isBlank()) {
                    try {
                        // extraer el precio y la cantidad del texfield
                        Double cantidad = Double.valueOf(texto);
                        Double precio = obtenerPrecio(child.getId());
                        // añade al total la multiplicacion de la cantidad añadida por el precio del producto
                        total += cantidad * precio;
                        // se suma a la variable impuesto el 0,7% de cada producto seleccionado asumiendo que el impuesto ya está incluido en cada precio
                        impuesto += cantidad * (precio - precio / 1.07);
                    } catch (NumberFormatException e) {
                        // En caso de escribir un caracter que no sea un número se informa al usuario
                        Utils.mostrarDialogo("Error con el tipo de valor introducido.", "Introduzca un valor númerico.");
                        // y se pone el textfield en blanco
                        tf.setText("");
                    }
                }
            }
        }
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
                return productoFinal.getPrecio();
            }
        }
        // En caso de no encontrarlo, devuelve 0.0
        return 0.0;
    }

    private void inicializarListeners() {
        // Bucle para recorrer todos los textfields
        for (Node child : textFBox.getChildrenUnmodifiable()) {
            // Si el nodo es un objeto TextField, le añade un listener que llama al método calcularTotal()
            if (child instanceof TextField) {
                TextField textField = (TextField) child;
                // Añadir listener para actualizar el total en cada cambio que se produzca
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    calcularTotal(); // Llama a un método que calcula y actualiza el total
                });
            }
        }
    }

    @FXML
    private void limpiar(){
        // Recorre todos los textfields y los vacía
        for (Node child : textFBox.getChildrenUnmodifiable()) {
            if (child instanceof TextField) {
                ((TextField) child).setText("");
            }
        }
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