package com.aerolinea;

public class VueloNacional extends Vuelo{

	private double valorRefrigerios;
	private double[] precio;
	
	public VueloNacional(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos, double[] precio, double valorRefrigerio) {
		super(origen, destino, fecha, tripulantes, cantAsientos);
		this.precio=precio;
		this.valorRefrigerios=valorRefrigerio;
		// TODO Apéndice de constructor generado automáticamente
	}

}
