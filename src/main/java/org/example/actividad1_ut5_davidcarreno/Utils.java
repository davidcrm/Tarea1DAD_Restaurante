package org.example.actividad1_ut5_davidcarreno;

import javafx.scene.control.Alert;

public class Utils {
    public static void mostrarDialogo(String titulo, String mensaje) {
        // Crear un cuadro de dialogo
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo); // Muestra el título pasado por parametro
        alert.setHeaderText(null);
        alert.setContentText(mensaje); // Establece el mensaje pasado por parámetro

        // Muestra el diálogo y espera que el usuario lo cierre
        alert.showAndWait();
    }
}
