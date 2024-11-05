package com.aerolinea;

public class Asiento {

	private int numero;
	private Boolean libre;
	private boolean comprado;

	public Asiento(int numero, Boolean estado, boolean comprado) {
		this.numero = numero;
		this.libre = estado;
		this.comprado = comprado;
	}

	public boolean ocupado() {
		return comprado;
	}

	public void vendido(boolean ocupado) {
		this.comprado = true;
		this.libre = ocupado;
	}

	public int numAsiento() {
		return numero;
	}

}
