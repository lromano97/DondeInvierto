package com.caia.dondeinvierto.models;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import iceblock.IBlock;
import iceblock.connection.ConnectionManager;

public class DBSession {
	
	private ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	
	public void addIndicador(Indicador unIndicador) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, InstantiationException{
		Connection conn =  ConnectionManager.getConnection();
		indicadores.add(unIndicador);
		IBlock.insert(conn, Indicador.class, unIndicador);
		
	}
	
	public ArrayList<Indicador> getIndicadores(){
		return indicadores;
	}
	
	/*
	public Cotizacion obtenerValorCuenta(String nombreCuenta, int anio) {

		int posicion;
		Cotizacion cotizacionARetornar;
		for(posicion = 0; posicion<indicadores.size(); posicion++) {
			if(nombreCuenta.equals(cotizaciones.get(posicion).getCuenta()) && cotizaciones.get(posicion).getAnio() == anio) {
				cotizacionARetornar = cotizaciones.get(posicion);
				return cotizacionARetornar;
			}
		}
		return null;
	
	}*/
	
	
	
	/*
	public Indicador obtenerIndicador(String nombreIndicador) {
		int posicion;
		Indicador indiARetornar;
		for(posicion = 0; posicion<indicadores.size(); posicion++) {
			if(nombreIndicador.equals(indicadores.get(posicion).getNombre())) {
				indiARetornar =  new Indicador(indicadores.get(posicion).getNombre(), indicadores.get(posicion).getExpresion());
				return indiARetornar;
			}
		}
		return null;
	}
	*/
}
