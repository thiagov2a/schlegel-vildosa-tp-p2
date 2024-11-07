package com.aerolinea;

public class Pasaje {

	private Cliente cliente;
	private Vuelo vuelo;
	private Asiento asiento;

	public Pasaje(int i) {
		Asiento asiento = new Asiento(i);
		this.asiento = asiento;
	}

	public Pasaje(Pasajero cliente, Vuelo vuelo, Asiento asiento) {
		this.cliente = cliente;
		this.vuelo = vuelo;
		this.asiento = asiento;
	}

	public void comprar(boolean ocupar) {
		this.asiento.vendido(ocupar);
	}

	public boolean verificarAsiento() {
		return this.asiento.isOcupado();
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
		return asiento.getNumero();
	}

	public void asignarCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Asiento getAsiento() {
		return this.asiento;
	}

}
