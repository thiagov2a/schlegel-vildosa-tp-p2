package com.aerolinea;

public class VueloPrivado extends Vuelo {

	private double precioPorJet;
	private int dniComprador;
	private int[] acompaniantes;
	private int jetsNecesarios;

	private int CAPACIDAD_POR_JET = 15;

	public VueloPrivado(String codigo, String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		super(codigo, origen, destino, fecha, tripulantes);
		this.precioPorJet = precio;
		this.dniComprador = dniComprador;
		this.acompaniantes = acompaniantes;
		this.jetsNecesarios = Math.ceilDiv(acompaniantes.length + 1, CAPACIDAD_POR_JET); // Se le suma el comprador
	}

	public double precioViaje() {
		return jetsNecesarios * precioPorJet * 1.30;
	}

	public String detalles() {
		return super.detalles("PRIVADO") + " (" + this.jetsNecesarios + ")";
	}
}
