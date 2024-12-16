package org.example.actividad1_ut5_davidcarreno;

import java.util.ArrayList;

public class Carrito {
    private ArrayList<Producto> carrito;
    private double total;

    public Carrito() {
        this.carrito = new ArrayList<Producto>();
    }

    public ArrayList<Producto> getCarrito() {
        return carrito;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void anyadirProducto(Producto p){
        carrito.add(p);
        this.total += p.getSubtotal();
    }

    public void listarCarrito(){
        for (Producto p : carrito){
            System.out.println(p.getNombre());
        }
    }
    public boolean contains(Producto p) {
        for (Producto producto : carrito) {
            if (producto.equals(p)) {
                return true;
            }
        }
        return false;
    }
    public void removeProducto(Producto p) {
        carrito.remove(p);
    }
}
