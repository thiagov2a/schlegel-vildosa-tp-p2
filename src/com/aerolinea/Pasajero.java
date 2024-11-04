package com.aerolinea;

import java.util.ArrayList;
import java.util.List;

public class Pasajero extends Cliente {

	private List<Pasaje> pasajes;
	
	public Pasajero(int dni, String nombre, String telefono) {
		super(dni, nombre, telefono);
		this.pasajes = new ArrayList<>();
	}

	@Override
	public int dniCliente() {
		// TODO Apéndice de método generado automáticamente
		return super.dniCliente();
	}

}
