package com.aerolinea;

public abstract class Vuelo {

	protected String origen;
	protected String destino;
	protected String fecha;
	protected int tripulantes;
	protected String codigo;

	public Vuelo(String origen, String destino, String fecha, int tripulantes, String codigo) {
		this.origen = origen;
		this.destino = destino;
		this.fecha = fecha;
		this.tripulantes = tripulantes;
		this.codigo=codigo;
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
	
	
	public String toString(String tipo) {
        StringBuilder sb = new StringBuilder();
        sb.append(codigo).append(" - ")
          .append(origen).append(" - ")
          .append(destino).append(" - ")
          .append(fecha).append(" - ")
          .append(tipo);
        return sb.toString();
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
}
