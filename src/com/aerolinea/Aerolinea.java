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
	private Map<String, Double> recaudacionPorDestino;

	public Aerolinea(String nombre, String cuit) {
		this.nombre = nombre;
		this.cuit = cuit;
		this.vuelos = new HashMap<>();
		this.clientes = new HashMap<>();
		this.aeropuertos = new HashMap<>();
		this.recaudacionPorDestino = new HashMap<>();
		this.cantVuelos = 0;
	}

	@Override
	public void registrarCliente(int dni, String nombre, String telefono) {
		if (clientes.containsKey(dni)) {
			throw new RuntimeException("El cliente con DNI " + dni + " ya está registrado.");
		}

		Cliente cliente = new Cliente(dni, nombre, telefono);
		clientes.put(dni, cliente);
	}

	@Override
	public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
		if (aeropuertos.containsKey(nombre)) {
			throw new RuntimeException("El aeropuerto " + nombre + " ya está registrado.");
		}

		Aeropuerto aeropuerto = new Aeropuerto(nombre, pais, provincia, direccion);
		aeropuertos.put(nombre, aeropuerto);
		recaudacionPorDestino.put(nombre, 0.0); // Inicializa la recaudación en 0
	}

	private void validarVuelo(String destino, String paisEsperado, String fecha) {
		if (!aeropuertos.containsKey(destino)) {
			throw new RuntimeException("El destino " + destino + " no está registrado.");
		}

		// Si paisEsperado está vacío ignoramos esta validación
		if (paisEsperado != null && !paisEsperado.isEmpty()) {
			if (!aeropuertos.get(destino).compararPais(paisEsperado)) {
				throw new RuntimeException("El destino " + destino + " no pertenece a " + paisEsperado + ".");
			}
		}

		if (fechaPosterior(fecha)) {
			throw new RuntimeException("La fecha ingresada no es válida.");
		}
	}

	@Override
	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		validarVuelo(destino, "Argentina", fecha);

		String codigo = generarCodigo("PUB");
		VueloNacional vueloNacional = new VueloNacional(codigo, origen, destino, fecha, tripulantes, cantAsientos,
				precios, valorRefrigerio);
		vuelos.put(codigo, vueloNacional);
		return codigo;
	}

	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		validarVuelo(destino, "", fecha);

		String codigo = generarCodigo("PUB");
		VueloInternacional vueloInternacional = new VueloInternacional(codigo, origen, destino, fecha, tripulantes,
				cantAsientos, valorRefrigerio, precios, cantRefrigerios, escalas);
		vuelos.put(codigo, vueloInternacional);
		return codigo;
	}

	@Override
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		validarVuelo(destino, "", fecha);

		String codigo = generarCodigo("PRI");
		VueloPrivado vueloP = new VueloPrivado(codigo, origen, destino, fecha, tripulantes, precio, dniComprador,
				acompaniantes);
		vuelos.put(codigo, vueloP);

		double recaudacionAnterior = recaudacionPorDestino.get(destino);
		double recaudacionNueva = recaudacionAnterior + vueloP.precioViaje();
		recaudacionPorDestino.put(destino, recaudacionNueva);

		return codigo;
	}

	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo) {

		Map<Integer, String> asientosDisponibles = new HashMap<>();

		int num = 0;

		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
			int[] asientos = new int[2];
			asientos = vueloN.getCantAsientos();
			for (int i = 0; i < asientos.length; i++) {
				for (int j = 0; j < asientos[i]; j++) {

					if (vueloN.getPasajes()[i + j].verificarAsiento()) {
						asientosDisponibles.put(vueloN.getPasajes()[num].numAsiento(), vueloN.tipoAsiento(num));
					}
					num++;
				}
			}
		}

		if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			int[] asientos = new int[3];
			asientos = vueloI.getCantAsientos();
			for (int i = 0; i < asientos.length; i++) {
				for (int j = 0; j < asientos[i]; j++) {

					if (vueloI.getPasajes()[num].verificarAsiento()) {
						asientosDisponibles.put(vueloI.getPasajes()[num].numAsiento(), vueloI.tipoAsiento(num));
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

		/* Va comparando cada tipo de vuelo para realizar el metodo necesario */
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
			vueloN.cargarCliente(nroAsiento - 1, clientes.get(dni));
			codigoPasaje = vueloN.comprarPasaje(nroAsiento - 1, aOcupar);
		}
		if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			vueloI.cargarCliente(nroAsiento - 1, clientes.get(dni));

			String destino = vuelos.get(codVuelo).destino();
			double recaudacionAnterior = recaudacionPorDestino.get(destino);

			double recaudacionNueva = recaudacionAnterior + vueloI.recaudado(nroAsiento);
			recaudacionPorDestino.put(destino, recaudacionNueva);

			codigoPasaje = vueloI.comprarPasaje(nroAsiento - 1, aOcupar);
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
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
			vueloN.getPasajes()[nroAsiento - 1].asignarCliente(null);
		} else {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			vueloI.getPasajes()[nroAsiento - 1].asignarCliente(null);
		}
	}

	@Override
	public void cancelarPasaje(int dni, int codPasaje) {

		for (Map.Entry<String, Vuelo> entry : vuelos.entrySet()) {
			if (entry instanceof VueloNacional) {
				VueloNacional vueloN = (VueloNacional) entry;
				Pasaje[] pa = vueloN.getPasajes();
				for (Pasaje pasaje : pa) {
					if (pasaje.dniPasajero() == dni) {
						pasaje.asignarCliente(null);
					}
				}
			}

			if (entry instanceof VueloInternacional) {
				VueloInternacional vueloI = (VueloInternacional) entry;
				Pasaje[] pa = vueloI.getPasajes();
				for (Pasaje pasaje : pa) {
					if (pasaje.dniPasajero() == dni) {
						pasaje.asignarCliente(null);
					}
				}
			}

		}

	}

	@Override
	public List<String> cancelarVuelo(String codVuelo) {
		List<String> pasajesSinProgramar = new ArrayList<>();

		// Consultamos vuelos similares para el reenvío de los pasajes
		List<String> vuelosRemplazos = consultarVuelosSimilares(vuelos.get(codVuelo).origen(),
				vuelos.get(codVuelo).destino(), vuelos.get(codVuelo).fecha());

		// Obtenemos los pasajes según el tipo de vuelo
		Pasaje[] pasajes = obtenerPasajesVuelo(codVuelo);

		if (pasajes != null) {
			for (Pasaje pasaje : pasajes) {
				if (pasaje.verificarAsiento()) {
					// Intentamos reprogramar el pasaje en vuelos similares
					boolean reprogramado = false;
					for (String cod : vuelosRemplazos) {
						if (reprogramarPasajeEnVuelo(cod, pasaje)) {
							pasajesSinProgramar.add(formatearPasaje(pasaje, cod));
							reprogramado = true;
							break;
						}
					}

					// Si no se pudo reprogramar el pasaje, lo cancelamos
					if (!reprogramado) {
						pasajesSinProgramar.add(formatearPasaje(pasaje, "CANCELADO"));
					}
				}
			}
		}

		return pasajesSinProgramar;
	}

	// Método auxiliar para obtener los pasajes de un vuelo según su código
	private Pasaje[] obtenerPasajesVuelo(String codVuelo) {
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			return ((VueloNacional) vuelos.get(codVuelo)).getPasajes();
		} else if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			return ((VueloInternacional) vuelos.get(codVuelo)).getPasajes();
		}
		return null;
	}

	// Método para intentar reprogramar un pasaje en un vuelo
	private boolean reprogramarPasajeEnVuelo(String codVuelo, Pasaje pasaje) {
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			return reprogramarPasajeEnVueloNacional(codVuelo, pasaje);
		} else if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			return reprogramarPasajeEnVueloInternacional(codVuelo, pasaje);
		}
		return false;
	}

	// Método para reprogramar en un vuelo nacional
	private boolean reprogramarPasajeEnVueloNacional(String codVuelo, Pasaje pasaje) {
		VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
		Pasaje[] pasajes = vueloN.getPasajes();
		for (int i = pasaje.numAsiento() - 1; i < pasajes.length; i++) {
			if (!pasajes[i].verificarAsiento()) {
				pasajes[i].comprarAsiento(pasaje.libre());
				return true;
			}
		}
		return false;
	}

	// Método para reprogramar en un vuelo internacional
	private boolean reprogramarPasajeEnVueloInternacional(String codVuelo, Pasaje pasaje) {
		VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
		Pasaje[] pasajes = vueloI.getPasajes();
		for (int i = pasaje.numAsiento() - 1; i < pasajes.length; i++) {
			if (!pasajes[i].verificarAsiento()) {
				pasajes[i].comprarAsiento(pasaje.libre());
				return true;
			}
		}
		return false;
	}

	// Método para formatear la salida del pasaje
	private String formatearPasaje(Pasaje pasaje, String codigoVuelo) {
		return pasaje.dniPasajero() + " - " + pasaje.nombrePasajero() + " - " + pasaje.telefonoPasajero() + " - "
				+ codigoVuelo;
	}

	@Override
	public double totalRecaudado(String destino) {
		double recadudado = 0;
		if (!vuelos.containsKey(destino)) {

			recadudado = recaudacionPorDestino.get(destino);
		}
		return recadudado;
	}

	@Override
	public String detalleDeVuelo(String codVuelo) {
		String datos = "";
		Vuelo vuelo = vuelos.get(codVuelo);

		if (vuelo instanceof VueloNacional) {
			datos = ((VueloNacional) vuelo).detalles();
		} else if (vuelo instanceof VueloInternacional) {
			datos = ((VueloInternacional) vuelo).detalles();
		} else if (vuelo instanceof VueloPrivado) {
			datos = ((VueloPrivado) vuelo).detalles();
		}

		return datos;
	}

	private boolean fechaPosterior(String fecha) {
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

	private String generarCodigo(String tipo) {
		this.cantVuelos++;
		return cantVuelos + "-" + tipo;
	}

}
