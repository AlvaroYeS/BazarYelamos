package com.bazar.bazaryelamos;

public class Producto {
	String nombre;
	int id, precio, stock;

	public Producto(String nombre, int id, int precio, int stock) {
		this.nombre = nombre;
		this.id = id;
		this.precio = precio;
		this.stock = stock;
	}

	public String getNombre() {
		return nombre;
	}

	public int getId() {
		return id;
	}

	public int getPrecio() {
		return precio;
	}

	public int getStock() {
		return stock;
	}
}
