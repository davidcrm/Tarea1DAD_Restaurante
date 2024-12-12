package org.example.actividad1_ut5_davidcarreno;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<Pedido, String> columnaNombre;
    @FXML
    private TableColumn<Pedido, Double> columnaPrecio;
    @FXML
    private ObservableList<Pedido> listaProductos;

    private boolean isModoClaro = true;
    @FXML
    public void initialize() {
        parent.getStylesheets().add(getClass().getResource("/org/example/actividad1_ut5_davidcarreno/styles/lightMode.css").toExternalForm());
        listaProductos = FXCollections.observableArrayList(
                new Pedido("Agua", 1.5),
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

        System.out.println(obtenerPrecio("Hamburguesa"));
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

    private void calcularTotal(ActionEvent event){

    }

    private double obtenerPrecio(String nombreProducto){
        List listaOrdenada = listaProductos.stream().toList();
        for (int i = 0; i < listaOrdenada.size(); i++) {
            Pedido pedido = (Pedido) listaOrdenada.get(i);
            String nombre = pedido.getNombre();
            if (nombreProducto.equals(nombre)){
                Pedido producto = (Pedido) listaOrdenada.get(i);
                return producto.getPrecio();
            }
        }
        return 0.0;
    }
}