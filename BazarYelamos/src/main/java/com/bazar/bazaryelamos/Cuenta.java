package com.bazar.bazaryelamos;

public class Cuenta {
	int total;
	String nombre;

	public Cuenta(int total, String nombre) {
		this.total = total;
		this.nombre = nombre;
	}

	public int getTotal() {
		return total;
	}

	public String getNombre() {
		return nombre;
	}
}
