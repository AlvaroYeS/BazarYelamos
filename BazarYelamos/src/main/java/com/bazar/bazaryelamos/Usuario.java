package com.bazar.bazaryelamos;

public class Usuario {
	int id, edad;
	String nombre, apellidos, direccion;
	String email;

	public Usuario(int id, int edad, String nombre, String apellidos, String email) {
		this.id = id;
		this.edad = edad;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	public Usuario(int edad, String nombre, String direccion, String email, int id) {
		this.id = id;
		this.edad = edad;
		this.nombre = nombre;
		this.direccion = direccion;
		this.email = email;
	}

	public Usuario(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public int getEdad() {
		return edad;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getEmail() {
		return email;
	}

	public String getDireccion() {
		return direccion;
	}
}
