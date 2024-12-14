package org.example.actividad1_ut5_davidcarreno;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

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
    private TableColumn<Pedido, String> columnaNombre;
    @FXML
    private TableColumn<Pedido, Double> columnaPrecio;
    @FXML
    private ObservableList<Pedido> listaProductos;
    @FXML
    private TextField totalTF, impuestoTF;
    private boolean isModoClaro = true;
    @FXML
    public void initialize() {
        parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        listaProductos = FXCollections.observableArrayList(
                new Pedido("Gaseosa", 3.0),
                new Pedido("Refresco", 2.0),
                new Pedido("Cerveza", 2.5),
                new Pedido("Ensalada", 3.5),
                new Pedido("Hamburguesa", 3.0),
                new Pedido("Perrito", 2.5),
                new Pedido("Sandwich", 2.5),
                new Pedido("Postre", 3.0)

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
    private double calcularTotal() {
        double total = 0.0;
        double impuesto = 0.0;
        for (Node child : textFBox.getChildrenUnmodifiable()) {
            // Verifica que el Node sea un TextField
            if (child instanceof TextField) {
                TextField tf = (TextField) child;
                // Ignorar TextFields vacíos o nulos
                String texto = tf.getText();
                if (texto != null && !texto.isBlank()) {
                    try {
                        Double cantidad = Double.valueOf(texto);
                        Double precio = obtenerPrecio(child.getId());
                        total += cantidad * precio;
                        impuesto += cantidad / 1.07;
                    } catch (NumberFormatException e) {
                        Utils.mostrarDialogo("Error con el tipo de valor introducido.", "Introduzca un valor númerico.");
                        tf.setText("");
                    }
                }
            }
        }
        totalTF.setText(String.format("%.2f", total) + "€");
        impuestoTF.setText(String.format("%.2f", impuesto) + "€");
        return total;
    }

    private double obtenerPrecio(String nombreProducto){
        // Devuelve una lista con los productos
        List listaOrdenada = listaProductos.stream().toList();
        // Recorremos la lista y guardamos en variables el objeto y el nombre
        for (int i = 0; i < listaOrdenada.size(); i++) {
            Pedido pedido = (Pedido) listaOrdenada.get(i);
            String nombre = pedido.getNombre();
            // Comprobamos que el nombre del objeto iterado es igual al que pasamos por parametro
            if ((nombreProducto.substring(0, 1).toUpperCase() + nombreProducto.substring(1).toLowerCase()).equals(nombre)){
                Pedido producto = (Pedido) listaOrdenada.get(i);
                // Devolvemos el precio de ese producto
                return producto.getPrecio();
            }
        }

        return 0.0;
    }

    private void inicializarListeners() {
        for (Node child : textFBox.getChildrenUnmodifiable()) {
            if (child instanceof TextField) {
                TextField textField = (TextField) child;

                // Añadir listener para actualizar el total en cada cambio
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    calcularTotal(); // Llama a un método que calcula y actualiza el total
                });
            }
        }
    }

    @FXML
    private void limpiar(){
        for (Node child : textFBox.getChildrenUnmodifiable()) {
            if (child instanceof TextField) {
                ((TextField) child).setText("");
            }
        }
    }
    @FXML
    private void cerrar(){
        System.exit(0);
    }

    @FXML
    private void aceptar(){
        Utils.mostrarDialogo("HA COMPLETADO SU PEDIDO.","El total de su pedido es de: " + calcularTotal() + " €");
        System.exit(0);
    }

}