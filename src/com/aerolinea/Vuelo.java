package com.aerolinea;

public abstract class Vuelo {
	
	private String origen;
	private String destino;
	private String fecha;
	private int tripulantes;
	private int[] cantAsientos;

	public Vuelo(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos) {
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.tripulantes = tripulantes;
		this.cantAsientos = cantAsientos;
	}
	
}
