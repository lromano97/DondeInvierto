package com.caia.dondeinvierto.models;

import java.util.ArrayList;

public class Database {
	
	private ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	private ArrayList<Cotizacion> cotizaciones = new ArrayList<Cotizacion>();
	private ArrayList<String> empresas = new ArrayList<String>();
	private ArrayList<String> cuentas = new ArrayList<String>();
	private ArrayList<Integer> anios = new ArrayList<Integer>();
	
	public boolean esVacio(){
		return cotizaciones.isEmpty();
	}
	
	public void addCotizacion(Cotizacion unaCotizacion){
		cotizaciones.add(unaCotizacion);
	}
	
	public void addIndicador(Indicador unIndicador){
		indicadores.add(unIndicador);
	}
	
	public ArrayList<Indicador> getIndicadores(){
		return indicadores;
	}
	
	public ArrayList<Cotizacion> getCotizaciones(){
		return cotizaciones;
	}
	
	public ArrayList<String> getCuentas(){
		return cuentas;
	}
	
	public ArrayList<String> getEmpresas(){
		return empresas;
	}
	
	public ArrayList<Integer> getAnios() {
		return anios;
	}
	
	public void addEmpresa(String empresa) {
		empresas.add(empresa);
	}
	
	public void addCuenta(String cuenta) {
		cuentas.add(cuenta);
	}
	
	public void addAnio(int anio) {
		anios.add(anio);
	}
	
	public Cotizacion obtenerValorCuenta(String nombreCuenta, int anio) {

		int posicion;
		Cotizacion cotizacionARetornar;
		for(posicion = 0; posicion<indicadores.size(); posicion++) {
			if(nombreCuenta.equals(cotizaciones.get(posicion).getCuenta().getNombre()) && cotizaciones.get(posicion).getAnio() == anio) {
				cotizacionARetornar = cotizaciones.get(posicion);
				return cotizacionARetornar;
			}
		}
		return null;
	
	}
	
	
	public Indicador obtenerIndicador(String nombreIndicador) {
		int posicion;
		Indicador indiARetornar;
		for(posicion = 0; posicion<indicadores.size(); posicion++) {
			if(nombreIndicador.equals(indicadores.get(posicion).nombre)) {
				indiARetornar =  new Indicador(indicadores.get(posicion).nombre, indicadores.get(posicion).expresion);
				return indiARetornar;
			}
		}
		return null;
	}

}
