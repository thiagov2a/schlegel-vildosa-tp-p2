package com.aerolinea;

public class Pasaje {
	
	private Pasajero pasajero;
	private Vuelo vuelo;
	private Asiento asiento;
	
	public Pasaje(int i) {
		Asiento asiento = new Asiento(i, false, false);
		this.asiento = asiento;
	}

	public Pasaje(Pasajero pasajero, Vuelo vuelo, Asiento asiento) {
		this.pasajero = pasajero;
		this.vuelo = vuelo;
		this.asiento = asiento;
	}

	public void comprarAsiento(boolean ocupar) {
		asiento.vendido(ocupar);
	}
	
	public boolean verificarAsiento() {
		return asiento.ocupado();
	}
	
	public int dniPasajero() {
		return pasajero.dniCliente();
		}
	
	public int numAsiento() {
		return asiento.numAsiento();
	}
}
