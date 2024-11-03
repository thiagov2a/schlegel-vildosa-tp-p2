package com.aerolinea;

public class Asiento {
	
	private int numero;
	private Boolean libre;
	
	public Asiento(int numero, Boolean estado) {
		this.numero = numero;
		this.libre = estado;
	}
	
	public boolean ocupado() {
		return libre;
	}
}
