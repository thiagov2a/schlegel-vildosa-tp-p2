package com.aerolinea;

public class VueloNacional extends Vuelo {

	private double valorRefrigerios;
	private double[] precio;
	private int[] cantAsientos;
	private Pasaje[] pasajes;

	public VueloNacional(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos,
			double[] precio, double valorRefrigerio) {
		super(origen, destino, fecha, tripulantes);
		this.precio = precio;
		this.valorRefrigerios = valorRefrigerio;
		this.cantAsientos = cantAsientos;
		// TODO Apéndice de constructor generado automáticamente
		this.pasajes = cargarAsientos();
	}

	/*
	 * En este metodo rellena la lista de pasajes con cun objeto pasaje por cada
	 * asiento
	 */
	private Pasaje[] cargarAsientos() {
		Pasaje[] pasajes = new Pasaje[cantAsientos[0] + cantAsientos[1]];
		int num = 1;
		for (int i = 0; i < cantAsientos.length; i++) {
			for (int j = 0; j < cantAsientos[i]; j++) {
				Pasaje pasaje = new Pasaje(num);
				pasajes[num - 1] = pasaje;
				num++;
			}
		}
		return pasajes;
	}

	/*
	 * Va al asiento ingresado por parametro y se le cambia los booleanos para saber
	 * que fue vendido y si se va a esta ocupado
	 */
	public int comprarPasaje(int numAsiento, boolean ocupar) {
		pasajes[numAsiento].comprarAsiento(ocupar);
		return pasajes[numAsiento].dniPasajero() / 2 + numAsiento;
	}

	public Pasaje[] pasajes() {
		return pasajes;
	}

	public int[] asientos() {
		return cantAsientos;
	}

	public String tipoAsiento(int num) {
		String asiento = "";
		if (num < cantAsientos[0]) {
			asiento = "clase Turista";
		} else {
			asiento = "clase Ejecutiva";
		}
		return asiento;
	}

}
