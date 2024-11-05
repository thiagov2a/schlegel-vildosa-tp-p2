package com.aerolinea;

import java.util.ArrayList;
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
	private int cantVuelos; // Contador para crear los códigos de cada vuelo

	public Aerolinea(String nombre, String cuit) {
		this.nombre = nombre;
		this.cuit = cuit;
		this.vuelos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.aeropuertos = new HashMap<>();
		this.cantVuelos = 1;
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

		VueloNacional vueloN = new VueloNacional(origen, destino, fecha, tripulantes, cantAsientos, precios,
				valorRefrigerio);

		cantVuelos++;

		vuelos.put(cantVuelos + "-PUB", vueloN);
		return cantVuelos + "-PUB";
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

		VueloInternacional vueloI = new VueloInternacional(origen, destino, fecha, tripulantes, cantAsientos,
				valorRefrigerio, precios, cantRefrigerios, escalas);

		cantVuelos++;

		vuelos.put(cantVuelos + "-PUB", vueloI);

		return cantVuelos + "-PUB";
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

		VueloPrivado vueloP = new VueloPrivado(origen, destino, fecha, tripulantes, precio, dniComprador,
				acompaniantes);

		cantVuelos++;

		vuelos.put(cantVuelos + "-PRI", vueloP);

		return cantVuelos + "-PRI";
	}

	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo) {

		Map<Integer, String> asientosDisponibles = new HashMap<>();

		int num = 0;

		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
			int[] asientos = new int[2];
			asientos = vueloN.asientos();
			for (int i = 0; i < asientos.length; i++) {
				for (int j = 0; j < asientos[i]; j++) {

					if (vueloN.pasajes()[i + j].verificarAsiento()) {
						asientosDisponibles.put(vueloN.pasajes()[num].numAsiento(), vueloN.tipoAsiento(num));
					}
					num++;
				}
			}
		}

		if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			int[] asientos = new int[3];
			asientos = vueloI.asientos();
			for (int i = 0; i < asientos.length; i++) {
				for (int j = 0; j < asientos[i]; j++) {

					if (vueloI.pasajes()[num].verificarAsiento()) {
						asientosDisponibles.put(vueloI.pasajes()[num].numAsiento(), vueloI.tipoAsiento(num));
					}
					num++;
				}
			}
		}
		return asientosDisponibles;
	}

	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {

		if (!clientes.containsKey(dni)) {
			throw new RuntimeException("El cliente no se encuentra registrado.");
		}

		int codigoPasaje = 0;

		/*
		 * 
		 * 
		 * FALTA EL METODO DE LOS VUELOS PRIVADOS
		 * 
		 * 
		 */

		/* Va comparando cada tipo de vuelo para realizar el metodo necesario */
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
			codigoPasaje = vueloN.comprarPasaje(nroAsiento, aOcupar);
		}
		if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			codigoPasaje = vueloI.comprarPasaje(nroAsiento, aOcupar);
		}

		/*
		 * En el caso que el codigo sea 0 o menor significa que algun dato fue erroneo
		 */
		if (codigoPasaje <= 0) {
			throw new RuntimeException("Ocurrio un error y no se pudo realizar la compra.");
		}

		return codigoPasaje;
	}

	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String Fecha) {
		
		List<String> vuelosSimilares = new ArrayList<>();

		for (Map.Entry<String, Vuelo> entry : vuelos.entrySet()) {
			if (entry.getValue().vueloSimilar(origen, destino, Fecha)) {
				vuelosSimilares.add(entry.getKey());
			}
		}
		
		return vuelosSimilares;
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
		// Este código puede ir en otra parte del programa, momentaneamente esta acá

		// Extraer el día, mes y año usando substring
		int dia = Integer.parseInt(fecha.substring(0, 2)); // Separa los dias
		int mes = Integer.parseInt(fecha.substring(3, 5)); // Separa el mes
		int anio = Integer.parseInt(fecha.substring(6, 10)); // Separa el año

		// Obtener la fecha actual
		Calendar fechaActual = Calendar.getInstance();
		int anioActual = fechaActual.get(Calendar.YEAR);
		int mesActual = fechaActual.get(Calendar.MONTH) + 1;
		int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);

		// Comparar año, luego mes, luego día
		return (anio < anioActual) || (anio == anioActual && mes < mesActual)
				|| (anio == anioActual && mes == mesActual && dia < diaActual);
	}
}
