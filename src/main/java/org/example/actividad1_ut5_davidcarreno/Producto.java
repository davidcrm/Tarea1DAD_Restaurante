package org.example.actividad1_ut5_davidcarreno;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Producto {
    private final SimpleStringProperty nombre;
    private final SimpleDoubleProperty precio;

    public Producto(String nombre, Double precio) {
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleDoubleProperty(precio);
    }

    // Getters y setters
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public Double getPrecio() {
        return precio.get();
    }

    public void setPrecio(Double precio) {
        this.precio.set(precio);
    }

    // Propiedades (para compatibilidad con JavaFX)
    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public SimpleDoubleProperty precioProperty() {
        return precio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return nombre.get().equals(producto.getNombre());
    }

    @Override
    public int hashCode() {
        return nombre.get().hashCode();
    }
}
