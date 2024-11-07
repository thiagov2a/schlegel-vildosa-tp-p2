package com.aerolinea;

public class VueloInternacional extends VueloPublico {

	private String[] escalas;

	public VueloInternacional(String codigo, String origen, String destino, String fecha, int tripulantes,
			int[] cantAsientos, double valorRefrigerios, double[] precio, int cantRefrigerios, String[] escalas) {
		super(codigo, origen, destino, fecha, tripulantes, cantAsientos, precio);
		this.valorRefrigerios = valorRefrigerios;
		this.cantRefrigerios = cantRefrigerios;
		this.escalas = escalas;
	}

	public String detalles() {
		return super.detalles("INTERNACIONAL");
	}
}
