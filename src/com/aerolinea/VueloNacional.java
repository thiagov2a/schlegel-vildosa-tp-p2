package com.aerolinea;

public class VueloNacional extends Vuelo{

	private double valorRefrigerios;
	private double[] precio;
	private int[] cantAsientos;
	private Pasaje[] pasajes;
	
	public VueloNacional(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos, double[] precio, double valorRefrigerio) {
		super(origen, destino, fecha, tripulantes);
		this.precio=precio;
		this.valorRefrigerios=valorRefrigerio;
		this.cantAsientos=cantAsientos;
		// TODO Apéndice de constructor generado automáticamente
	}

	
}
