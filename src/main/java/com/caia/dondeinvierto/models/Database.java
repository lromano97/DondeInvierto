package com.caia.dondeinvierto.models;

import java.util.ArrayList;

public class Database {

	
	private static Database instance = null;
	ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	ArrayList<Cotizacion> cotizaciones = new ArrayList<Cotizacion>();
	ArrayList<String> empresas = new ArrayList<String>();
	ArrayList<String> cuentas = new ArrayList<String>();
	ArrayList<Integer> anios = new ArrayList<Integer>();
 	
	// Carga con datos del usuario
	protected Database(){}
	
	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	public void addRow(Cotizacion unaRow){
		cotizaciones.add(unaRow);
	}
	
	public void addIndicador(Indicador unIndicador){
		indicadores.add(unIndicador);
	}
	
	public ArrayList<Indicador> getIndicadores(){
		return indicadores;
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
			if(nombreCuenta.equals(cotizaciones.get(posicion).getCuenta().getNombre()) && cotizaciones.get(posicion).anio == anio) {
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
