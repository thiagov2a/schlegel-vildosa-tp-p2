package com.aerolinea;

public class VueloInternacional extends Vuelo {

	private double valorRefrigerios;
	private double[] precio;
	private int cantRefrigerios;
	private String[] escalas;
	private int[] cantAsientos;
	private Pasaje[] pasajes;


	public VueloInternacional(String origen, String destino, String fecha, int tripulantes, int[] cantAsientos,
			double valorRefrigerios, double[] precio, int cantRefrigerios, String[] escalas, String codigo) {
		super(origen, destino, fecha, tripulantes, codigo);
		this.cantRefrigerios = cantRefrigerios;
		this.precio = precio;
		this.valorRefrigerios = valorRefrigerios;
		this.cantAsientos = cantAsientos;
		this.pasajes = cargarAsientos();

	}

	/*
	 * En este metodo rellena la lista de pasajes con cun objeto pasaje por cada
	 * asiento
	 */
	private Pasaje[] cargarAsientos() {
		Pasaje[] pasajes = new Pasaje[cantAsientos[0] + cantAsientos[1] + cantAsientos[2]];
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
		} else if (num < cantAsientos[1]) {
			asiento = "clase Ejecutiva";
		} else {
			asiento = "Primera clase";
		}
		return asiento;
	}

	public void cargarCliente(int numAsiento, Cliente cliente) {
		pasajes[numAsiento].asignarCliente(cliente);
	}



	public double recaudado(int numAsiento) {
		double gananciaAsientos = 0;
		double costoRefrigerios = valorRefrigerios * cantRefrigerios;
		int totalAsientos=cantAsientos[0]+cantAsientos[1]+cantAsientos[2];
		if (numAsiento <= cantAsientos[0]) {
			gananciaAsientos = (precio[0] + costoRefrigerios)*1.2;
		} else if (numAsiento <= totalAsientos-cantAsientos[2] && numAsiento > cantAsientos[0]) {
			gananciaAsientos = (precio[1] + costoRefrigerios)*1.2;
		} else {
			gananciaAsientos = (precio[2] + costoRefrigerios)*1.2;
		}
		return gananciaAsientos;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		return super.toString("INTERNACIONAL");
		}
	
}