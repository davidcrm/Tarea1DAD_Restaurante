package org.example.actividad1_ut5_davidcarreno;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Pedido {
    private final SimpleStringProperty nombre;
    private final SimpleDoubleProperty cantidad;

    public Pedido(String nombre, Double cantidad) {
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleDoubleProperty(cantidad);
    }

    // Getter y setter para el nombre
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    // Getter y setter para la cantidad
    public Double getPrecio() {
        return cantidad.get();
    }

    public void setPrecio(Double cantidad) {
        this.cantidad.set(cantidad);
    }

    // Propiedades para las columnas
    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public SimpleDoubleProperty cantidadProperty() {
        return cantidad;
    }
}
