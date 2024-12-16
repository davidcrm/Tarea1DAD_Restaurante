package org.example.actividad1_ut5_davidcarreno;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private final Map<Producto, Integer> carrito; // Producto y su cantidad
    private double total;

    public Carrito() {
        this.carrito = new HashMap<>();
        this.total = 0.0;
    }

    public Map<Producto, Integer> getCarrito() {
        return carrito;
    }

    public double getTotal() {
        return total;
    }

    public void anyadirProducto(Producto p) {
        carrito.put(p, carrito.getOrDefault(p, 0) + 1);
        total += p.getPrecio(); // Sumar al total el precio del producto
    }

    public void removeProducto(Producto p) {
        if (carrito.containsKey(p)) {
            int cantidadActual = carrito.get(p);
            if (cantidadActual > 1) {
                carrito.put(p, cantidadActual - 1);
            } else {
                carrito.remove(p); // Si la cantidad es 1, se elimina del carrito
            }
            total -= p.getPrecio(); // Restar del total el precio del producto
            if (total < 0) total = 0; // Asegurar que no sea negativo
        }
    }

    public void listarCarrito() {
        for (Map.Entry<Producto, Integer> entry : carrito.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println(p.getNombre() + " x" + cantidad + " = " + (cantidad * p.getPrecio()));
        }
        System.out.println("Total: " + total);
    }
}
