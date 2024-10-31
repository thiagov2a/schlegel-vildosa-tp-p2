package com.aerolinea;

public class Pasaje {
	
	private Pasajero pasajero;
	private Vuelo vuelo;
	private Asiento asiento;
	
	public Pasaje(Pasajero pasajero, Vuelo vuelo, Asiento asiento) {
		this.pasajero = pasajero;
		this.vuelo = vuelo;
		this.asiento = asiento;
	}

}
