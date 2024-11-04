package com.aerolinea;

public class Cliente {
	
	private int dni;
	private String nombre;
	private String telefono;
	
	public Cliente(int dni, String nombre, String telefono) {
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
	}
	
	public int dniCliente() {
		return dni;
	}
}
