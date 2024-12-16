package org.example.actividad1_ut5_davidcarreno;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Producto {
    private final SimpleStringProperty nombre;
    private final SimpleDoubleProperty cantidad;

    private IntegerProperty cantidadEnCarrito;

    public Producto(String nombre, Double cantidad) {
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleDoubleProperty(cantidad);
        this.cantidadEnCarrito = new SimpleIntegerProperty(0);
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
        return cantidadEnCarrito.get();
    }

    public void setCantidadEnCarrito(IntegerProperty cantidadEnCarrito) {
        this.cantidadEnCarrito = cantidadEnCarrito;
    }

    public IntegerProperty cantidadEnCarritoProperty() {
        return cantidadEnCarrito;
    }

    public void aumentarCantidadProducto() {
        // suma uno a la cantidad que hay guarda en el carrito del producto
        this.cantidadEnCarrito.set(this.cantidadEnCarrito.get() + 1);
    }

    public double getSubtotal() {
        if (this.getCantidadEnCarrito() > 0 ) {
            return cantidadEnCarrito.get() * cantidad.get();
        }
        return 0.0;
    }

    public void eliminarDelCarrito() {
        // Comprueba si hay en el carrito y lo elimina
        if (this.cantidadEnCarrito.get() > 0) {
            this.cantidadEnCarrito.set(this.cantidadEnCarrito.get() - 1);
        }
        else {
            // si no, establece la cantidad a 0
            this.cantidadEnCarrito.set(0);
        }
    }
}
