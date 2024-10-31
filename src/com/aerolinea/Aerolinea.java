package com.aerolinea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {

	private String nombre;
	private String cuit;
	private List<Vuelo> vuelos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Aeropuerto> aeropuertos;

	public Aerolinea(String nombre, String cuit) {
		this.nombre = nombre;
		this.cuit = cuit;
		this.vuelos = new ArrayList<>();
		this.clientes = new HashMap<>();
		this.aeropuertos = new HashMap<>();
	}

	@Override
	public void registrarCliente(int dni, String nombre, String telefono) {
		if (clientes.containsKey(dni)) {
			throw new RuntimeException("El cliente ya se encuentra registrado.");
		}
		
		Cliente cliente = new Cliente(dni, nombre, telefono);
		clientes.put(dni, cliente);
	}

	@Override
	public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombre)) {
			throw new RuntimeException("El aeropuerto ya se encuentra registrado.");
		}
		
		Aeropuerto aeropuerto = new Aeropuerto(nombre, pais, provincia, direccion);
		aeropuertos.put(nombre, aeropuerto);
	}

	@Override
	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		
		
		return null;
	}

	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		
		return null;
	}

	@Override
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		
		return null;
	}

	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo) {
		
		return null;
	}

	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
		
		return 0;
	}

	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String Fecha) {
		
		return null;
	}

	@Override
	public void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
		
	}

	@Override
	public void cancelarPasaje(int dni, int codPasaje) {
		
	}

	@Override
	public List<String> cancelarVuelo(String codVuelo) {
		
		return null;
	}

	@Override
	public double totalRecaudado(String destino) {
		
		return 0;
	}

	@Override
	public String detalleDeVuelo(String codVuelo) {
		
		return null;
	}	
}
