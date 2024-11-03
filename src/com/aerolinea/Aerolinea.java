package com.aerolinea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {

	private String nombre;
	private String cuit;
	private Map<String, Vuelo> vuelos;
	private Map<Integer, Cliente> clientes;
	private Map<String, Aeropuerto> aeropuertos;
	private int cantVuelos; //contador para crear los codigos de cada vuelo
	
	public Aerolinea(String nombre, String cuit) {
		this.nombre = nombre;
		this.cuit = cuit;
		this.vuelos = new HashMap<>();
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

		/*
		Aca iria un if donde compararia la fecha ingresada por parametro y verifica si es mayor a la actual
		en el caso de que no saltaria una excepcion
		*/
		
		if (!aeropuertos.containsKey(destino)) {
			throw new RuntimeException("El destino ingresado no esta registrado.");
		} else if (!aeropuertos.get(destino).compararPais("Argentina")) {
			throw new RuntimeException("El destino ingresado pertenece a Argentina.");
		}
		
		VueloNacional vueloN = new VueloNacional(origen, destino, fecha, tripulantes, cantAsientos, precios, valorRefrigerio);
		
		cantVuelos++;
		
		vuelos.put(cantVuelos+"-PUB", vueloN);
		return cantVuelos+"-PUB";
	}

	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		/*
		Aca iria un if donde compararia la fecha ingresada por parametro y verifica si es mayor a la actual
		en el caso de que no saltaria una excepcion
		*/
		if (!aeropuertos.containsKey(destino)) {
			throw new RuntimeException("El destino ingresado no esta registrado.");
		}
		
		VueloInternacional vueloI = new VueloInternacional(origen, destino, fecha, tripulantes, cantAsientos, valorRefrigerio, precios, cantRefrigerios, escalas);
		
		cantVuelos++;
		
		vuelos.put(cantVuelos+"-PUB", vueloI);
		
		return cantVuelos+"-PUB";
	}

	@Override
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		
		/*
		Aca iria un if donde compararia la fecha ingresada por parametro y verifica si es mayor a la actual
		en el caso de que no saltaria una excepcion
		*/
		if (!aeropuertos.containsKey(destino)) {
			throw new RuntimeException("El destino ingresado no esta registrado.");
		}
		
		int[] cantAsientos = new int[15];
		
		VueloPrivado vueloP = new VueloPrivado(origen, destino, fecha, tripulantes, cantAsientos, precio, dniComprador, acompaniantes);
		
		cantVuelos++;
		
		vuelos.put(cantVuelos+"-PRI", vueloP);
		
		return cantVuelos+"-PRI";
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
