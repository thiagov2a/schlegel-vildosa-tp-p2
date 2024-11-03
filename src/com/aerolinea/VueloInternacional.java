package com.aerolinea;

public class VueloInternacional extends Vuelo {

	private double valorRefrigerios;
	private double[] precio;
	private int cantRefrigerios;
	private String[] escalas;
	private int[] cantAsientos;
	
	public VueloInternacional(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos, double valorRefrigerios,double[] precio,int cantRefrigerios, String[] escalas) {
		super(origen, destino, fecha, tripulantes);
		
		// TODO Apéndice de constructor generado automáticamente
		this.cantRefrigerios=cantRefrigerios;
		this.precio=precio;
		this.valorRefrigerios=valorRefrigerios;
		this.cantAsientos=cantAsientos;
	}

	

}
