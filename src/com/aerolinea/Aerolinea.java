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
		
		vueloN.cargarKey(cantVuelos + "-PUB");
		
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

		vueloI.cargarKey(cantVuelos + "-PUB");
		
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
		 * TODO;FALTA EL METODO DE LOS VUELOS PRIVADOS
		 * 
		 * 
		 */

		/* Va comparando cada tipo de vuelo para realizar el metodo necesario */
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
			VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
			vueloN.cargarCliente(nroAsiento - 1, clientes.get(dni));
			codigoPasaje = vueloN.comprarPasaje(nroAsiento - 1, aOcupar);
		}
		if (vuelos.get(codVuelo) instanceof VueloInternacional) {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			vueloI.cargarCliente(nroAsiento - 1, clientes.get(dni));
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
			vueloN.pasajes()[nroAsiento - 1].asignarCliente(null);
		} else {
			VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
			vueloI.pasajes()[nroAsiento - 1].asignarCliente(null);
		}
	}

	@Override
	public void cancelarPasaje(int dni, int codPasaje) {

		for (Map.Entry<String, Vuelo> entry : vuelos.entrySet()) {
			if (entry instanceof VueloNacional) {
				VueloNacional vueloN = (VueloNacional) entry;
				Pasaje[] pa = vueloN.pasajes();
				for (Pasaje pasaje : pa) {
					if (pasaje.dniPasajero() == dni) {
						pasaje.asignarCliente(null);
					}
				}
			}

			if (entry instanceof VueloInternacional) {
				VueloInternacional vueloI = (VueloInternacional) entry;
				Pasaje[] pa = vueloI.pasajes();
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

		List<String> vuelosRemplazos = consultarVuelosSimilares(vuelos.get(codVuelo).origen(),
				vuelos.get(codVuelo).destino(), vuelos.get(codVuelo).fecha());
		
		Pasaje[] pasajes = null;
		if (vuelos.get(codVuelo) instanceof VueloNacional) {
		    VueloNacional vueloN = (VueloNacional) vuelos.get(codVuelo);
		    pasajes = vueloN.pasajes();  
		} else if (vuelos.get(codVuelo) instanceof VueloInternacional) {
		    VueloInternacional vueloI = (VueloInternacional) vuelos.get(codVuelo);
		    pasajes = vueloI.pasajes();  
		}

		if (pasajes != null) {
		    for (Pasaje pasaje : pasajes) {
		    	if (pasaje.verificarAsiento()) {
		    		int numAsiento=pasaje.numAsiento();
		    		for (String cod : vuelosRemplazos) {
			    		if (vuelos.get(cod) instanceof VueloNacional) {
			    		    VueloNacional vueloN = (VueloNacional) vuelos.get(cod);
			    		    Pasaje[] pa = vueloN.pasajes();
			    		    for (int i = pasaje.numAsiento()-1; i < pa.length; i++) {
								if (!pa[i].verificarAsiento()) {
									pa[i].comprarAsiento(pasaje.libre());
									pasajesSinProgramar.add(pasaje.dniPasajero()+" - "+pasaje.nombrePasajero()
									+" - "+pasaje.telefonoPasajero()+" - "+vueloN.key());
								}
							}
			    		    
			    		} else if (vuelos.get(cod) instanceof VueloInternacional) {
			    		    VueloInternacional vueloI = (VueloInternacional) vuelos.get(cod);
			    		    Pasaje[] pa = vueloI.pasajes();
			    		    for (int i = pasaje.numAsiento()-1; i < pa.length; i++) {
								if (!pa[i].verificarAsiento()) {
									pa[i].comprarAsiento(pasaje.libre());
									pasajesSinProgramar.add(pasaje.dniPasajero()+" - "+pasaje.nombrePasajero()
									+" - "+pasaje.telefonoPasajero()+" - "+vueloI.key());
								}
							}
			    		    
			    		}else {
			    			
			    			pasajesSinProgramar.add(pasaje.dniPasajero()+" - "+pasaje.nombrePasajero()
							+" - "+pasaje.telefonoPasajero()+" - CANCELADO");
			    			
			    		}
					}
				}
		    	
		    	
		    }
		}
		
		vuelos.remove(codVuelo);
		
		return pasajesSinProgramar;
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
	
	/*
	 * 		for (Map.Entry<String, Vuelo> entry : vuelos.entrySet()) {
			if (entry instanceof VueloNacional) {
				VueloNacional vueloN = (VueloNacional) entry;
				Pasaje[] pa = vueloN.pasajes();
				for (Pasaje pasaje : pa) {
					if (pasaje.verificarAsiento()) {
						int n = 0;
						int asiento = pasaje.numAsiento();
						boolean bucle = true;
						while (n < vuelosRemplazos.size()) {
							VueloNacional vueloN2 = (VueloNacional) vuelos.get(vuelosRemplazos.get(n));
							for (int i = asiento-1; i < vueloN2.asientos().length; i++) {
								if (!vueloN2.pasajes()[i].verificarAsiento()) {
									vueloN2.pasajes()[i].comprarAsiento(bucle);
								}
							}
						}
					}

				}
			}
			if (entry instanceof VueloInternacional) {
				VueloInternacional vueloI = (VueloInternacional) entry;
				Pasaje[] pa = vueloI.pasajes();
				for (Pasaje pasaje : pa) {

				}
			}
		}
	 * 
	 * 
	 * */
	
	
}
