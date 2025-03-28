package com.aerolinea;

public class Aeropuerto {

	private String nombre;
	private String pais;
	private String provincia;
	private String direccion;

	public Aeropuerto(String nombre, String pais, String provincia, String direccion) {
		this.nombre = nombre;
		this.pais = pais;
		this.provincia = provincia;
		this.direccion = direccion;
	}

	public boolean compararPais(String paisAComparar) {
		if (paisAComparar == null) {
			return false;
		}
		return pais.equalsIgnoreCase(paisAComparar);
	}

}
