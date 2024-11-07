package com.aerolinea;

public class Asiento {

	private int numero;
	private boolean ocupado;
	private boolean comprado;

	public Asiento(int numero) {
		this.numero = numero;
		this.ocupado = false;
		this.comprado = false;
	}

	public void vendido(boolean ocupado) {
		this.comprado = true;
		this.ocupado = ocupado;
	}

	public int getNumero() {
		return numero;
	}

	public boolean isOcupado() {
		return ocupado;
	}

	public boolean isComprado() {
		return comprado;
	}

}
