package com.aerolinea;

import java.util.Calendar;
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

		if (fechaPosterior(fecha)) {
			throw new RuntimeException("Fecha ingresada no es valida.");
		}
		
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
		
		if (fechaPosterior(fecha)) {
			throw new RuntimeException("Fecha ingresada no es valida.");
		}
		
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
		
		if (fechaPosterior(fecha)) {
			throw new RuntimeException("Fecha ingresada no es valida.");
		}
		
		if (!aeropuertos.containsKey(destino)) {
			throw new RuntimeException("El destino ingresado no esta registrado.");
		}
		
		VueloPrivado vueloP = new VueloPrivado(origen, destino, fecha, tripulantes, precio, dniComprador, acompaniantes);
		
		cantVuelos++;
		
		vuelos.put(cantVuelos+"-PRI", vueloP);
		
		return cantVuelos+"-PRI";
	}

	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo) {
	//
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
	
	public boolean fechaPosterior(String fecha) {
		//este codigo puede ir en otra parte del programa, momentarneamente esta aca
	    // Extraer el día, mes y año usando substring
	    int dia = Integer.parseInt(fecha.substring(0, 2));    // Separa los dias
	    int mes = Integer.parseInt(fecha.substring(3, 5));    // Separa el mes
	    int anio = Integer.parseInt(fecha.substring(6, 10));  // Separa el año

	    // Obtener la fecha actual
	    Calendar fechaActual = Calendar.getInstance();
	    int anioActual = fechaActual.get(Calendar.YEAR);
	    int mesActual = fechaActual.get(Calendar.MONTH) + 1; 
	    int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);

	    // Comparar año, luego mes, luego día
	    return (anio < anioActual) || (anio == anioActual && mes < mesActual) || 
	            (anio == anioActual && mes == mesActual && dia < diaActual);
	    
	}
	
}
