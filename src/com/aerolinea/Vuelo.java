package com.aerolinea;

public abstract class Vuelo {

	protected String codigo;
	protected String origen;
	protected String destino;
	protected String fecha;
	protected int tripulantes;

	public Vuelo(String codigo, String origen, String destino, String fecha, int tripulantes) {
		this.codigo = codigo;
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.tripulantes = tripulantes;
	}

	public Boolean vueloSimilar(String orig, String dest, String fech) {
		if (orig.equals(origen) && dest.equals(destino)) {
			int diav = Integer.parseInt(fecha.substring(0, 2)); // Separa los dias
			int mesv = Integer.parseInt(fecha.substring(3, 5)); // Separa el mes
			int aniov = Integer.parseInt(fecha.substring(6, 10)); // Separa el año
			int fechav = aniov * 365 + mesv * 30 + diav;

			int dia = Integer.parseInt(fech.substring(0, 2)); // Separa los dias
			int mes = Integer.parseInt(fech.substring(3, 5)); // Separa el mes
			int anio = Integer.parseInt(fech.substring(6, 10)); // Separa el año
			int fecha = anio * 365 + mes * 30 + dia;

			if (fechav - fecha >= 0 && fechav - fecha <= 7) {
				return true;
			}
		}
		return false;
	}

	public String fecha() {
		return fecha;
	}

	public String origen() {
		return origen;
	}

	public String destino() {
		return destino;
	}

	public String detalles(String tipo) {
		StringBuilder sb = new StringBuilder();
		return sb.append(codigo).append(" - ").append(origen).append(" - ").append(destino).append(" - ").append(fecha)
				.append(" - ").append(tipo).toString();
	}
}
