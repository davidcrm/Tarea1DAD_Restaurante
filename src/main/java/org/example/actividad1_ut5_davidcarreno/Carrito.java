package org.example.actividad1_ut5_davidcarreno;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private final Map<Producto, Integer> carrito; // Producto y su cantidad
    private double total;
    // Constructor
    public Carrito() {
        this.carrito = new HashMap<>();
        this.total = 0.0;
    }
    // getter y setter
    public Map<Producto, Integer> getCarrito() {
        return carrito;
    }

    public double getTotal() {
        return total;
    }

    public void anyadirProducto(Producto p) {
        // Añade el producto al HashMap del carrito
        carrito.put(p, carrito.getOrDefault(p, 0) + 1);
        total += p.getPrecio(); // Suma al total el precio del producto
    }

    public void removeProducto(Producto p) {
        // Comprueba si el producto está en el carrito
        if (carrito.containsKey(p)) {
            // Guarda la cantidad del producto en una variable
            int cantidadActual = carrito.get(p);
            // Comprueba si la cantidad es mas de uno
            if (cantidadActual > 1) {
                // Actualiza su cantidad quitandole 1
                carrito.put(p, cantidadActual - 1);
            } else {
                // Si la cantidad es 1, se elimina del carrito
                carrito.remove(p);
            }
            // Resta del total el precio del producto
            total -= p.getPrecio();
            // Asegurar que no sea negativo
            if (total < 0) total = 0;
        }
    }
    // Metodo para pruebas
    public void listarCarrito() {
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println(p.getNombre() + " x" + cantidad + " = " + (cantidad * p.getPrecio()));
        }
        System.out.println("Total: " + total);
    }
}
