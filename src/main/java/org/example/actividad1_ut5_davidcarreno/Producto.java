package org.example.actividad1_ut5_davidcarreno;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Producto {
    private final SimpleStringProperty nombre;
    private final SimpleDoubleProperty cantidad;

    private int cantidadEnCarrito;

    public Producto(String nombre, Double cantidad) {
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleDoubleProperty(cantidad);
        this. cantidadEnCarrito = 0;
    }

    // Getter y setter para el nombre
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    // Getter y setter para la cantidad
    public Double getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(Double cantidad) {
        this.cantidad.set(cantidad);
    }

    // Propiedades para las columnas
    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public SimpleDoubleProperty cantidadProperty() {
        return cantidad;
    }

    // Getter y setter para la cantidad que hay en el carrito
    public int getCantidadEnCarrito() {
        return cantidadEnCarrito;
    }

    public void setCantidadEnCarrito(int cantidadEnCarrito) {
        this.cantidadEnCarrito = cantidadEnCarrito;
    }

    public void anyadirAlCarrito() {
        // suma uno a la cantidad que hay guarda en el carrito del producto
        this.cantidadEnCarrito++;
    }
    public double getSubtotal() {
        return cantidadEnCarrito * cantidad.get();
    }
    public void eliminarDelCarrito() {
        // Comprueba si hay en el carrito y lo elimina
        if (this.cantidadEnCarrito > 0) {
            this.cantidadEnCarrito--;
        }
        else {
            // si no, establece la cantidad a 0
            this.cantidadEnCarrito = 0;
        }
    }
}
