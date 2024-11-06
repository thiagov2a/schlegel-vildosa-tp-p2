package com.aerolinea;

public class VueloPrivado extends Vuelo {

	private double precio;
	private int dniComprador;
	private int[] acompaniantes;
	private int jets;

	public VueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio, int dniComprador,
			int[] acompaniantes) {
		super(origen, destino, fecha, tripulantes);
		this.acompaniantes = acompaniantes;
		this.dniComprador = dniComprador;
		this.precio = precio;
	}

	public double PrecioViaje() {
		this.jets = Math.ceilDiv(acompaniantes.length, 15) ;
		System.out.println(jets + " asdas");
		double precioJets = jets * precio;
		return precioJets * 1.30;
	}

}
