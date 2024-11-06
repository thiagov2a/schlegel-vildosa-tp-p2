package com.aerolinea;

public class VueloPrivado extends Vuelo {

	private double precio;
	private int dniComprador;
	private int[] acompaniantes;
	private int jets;

	public VueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio, int dniComprador,
			int[] acompaniantes, String codigo) {
		super(origen, destino, fecha, tripulantes, codigo);
		this.acompaniantes = acompaniantes;
		this.dniComprador = dniComprador;
		this.precio = precio;
		this.jets = Math.ceilDiv(acompaniantes.length+1, 15); //Se le suma el comprador
	}

	public double PrecioViaje() {
		
		double precioJets = jets * precio;
		return precioJets * 1.30;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(codigo)
		.append(" - ")
		.append(origen)
		.append(" - ")
		.append(destino)
		.append(" - ")
		.append(fecha)
		.append(" - ")
		.append("PRIVADO")
		.append(" (")
		.append(jets)
		.append(")");
		return sb.toString();
	}

}

// CodigoVuelo - Nombre Aeropuerto de salida - Nombre Aeropuerto de llegada - 
//                     fecha de salida - [NACIONAL /INTERNACIONAL / PRIVADO + cantidad de jets necesarios].
