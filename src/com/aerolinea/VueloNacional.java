package com.aerolinea;

public class VueloNacional extends VueloPublico {

	public VueloNacional(String codigo, String origen, String destino, String fecha, int tripulantes,
			int[] cantAsientos, double[] precio, double valorRefrigerio) {
		super(codigo, origen, destino, fecha, tripulantes, cantAsientos, precio);
		this.valorRefrigerios = valorRefrigerio;
	}

	public String detalles() {
		return super.detalles("NACIONAL");
	}
}
