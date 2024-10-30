package com.aerolinea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Aerolinea implements IAerolinea {
	
	private String nombre;
	private String cuit;
	private List<Vuelo> vuelos;
	private List<Cliente> clientes;
	private List<Aeropuerto> aeropuertos;

	public Aerolinea(String nombre, String cuit) {
		this.nombre = nombre;
		this.cuit = cuit;
		this.vuelos = new ArrayList<>();
		this.clientes = new ArrayList<>();
		this.aeropuertos = new ArrayList<>();
	}

	@Override
	public void registrarCliente(int dni, String nombre, String telefono) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registrarAeropuerto(String nombre, String pais, String provincia, String direccion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String registrarVueloPublicoNacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, double[] precios, int[] cantAsientos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registrarVueloPublicoInternacional(String origen, String destino, String fecha, int tripulantes,
			double valorRefrigerio, int cantRefrigerios, double[] precios, int[] cantAsientos, String[] escalas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String VenderVueloPrivado(String origen, String destino, String fecha, int tripulantes, double precio,
			int dniComprador, int[] acompaniantes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> asientosDisponibles(String codVuelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int venderPasaje(int dni, String codVuelo, int nroAsiento, boolean aOcupar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> consultarVuelosSimilares(String origen, String destino, String Fecha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelarPasaje(int dni, String codVuelo, int nroAsiento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarPasaje(int dni, int codPasaje) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> cancelarVuelo(String codVuelo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double totalRecaudado(String destino) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String detalleDeVuelo(String codVuelo) {
		// TODO Auto-generated method stub
		return null;
	}	

}
