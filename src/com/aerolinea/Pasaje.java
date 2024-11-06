package com.aerolinea;

public class Pasaje {

	private Cliente cliente;
	private Vuelo vuelo;
	private Asiento asiento;

	public Pasaje(int i) {
		Asiento asiento = new Asiento(i, false, false);
		this.asiento = asiento;
	}

	public Pasaje(Pasajero cliente, Vuelo vuelo, Asiento asiento) {
		this.cliente = cliente;
		this.vuelo = vuelo;
		this.asiento = asiento;
	}

	public void comprarAsiento(boolean ocupar) {
		asiento.vendido(ocupar);
	}

	public boolean verificarAsiento() {
		return asiento.ocupado();
	}

	public int dniPasajero() {
		return cliente.dniCliente();
	}
	
	public String telefonoPasajero() {
		return cliente.telefonoCliente();
	}
	
	public String nombrePasajero() {
		return cliente.nombreCliente();
	}
	
	public int numAsiento() {
		return asiento.numAsiento();
	}
	
	public void asignarCliente(Cliente cliente) {
		this.cliente=cliente;
	}
	
	public boolean libre() {
		return asiento.libre();
	}
}
