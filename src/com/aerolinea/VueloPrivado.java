package com.aerolinea;

public class VueloPrivado extends Vuelo{
	
	private double precio;
	private int dniComprador;
	private int[] acompaniantes;
	
	public VueloPrivado(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos, double precio, int dniComprador, int[] acompaniantes) {
		super(origen, destino, fecha, tripulantes, cantAsientos);
		// TODO Apéndice de constructor generado automáticamente
		this.acompaniantes=acompaniantes;
		this.dniComprador=dniComprador;
		this.precio=precio;
	}

	
	
}
