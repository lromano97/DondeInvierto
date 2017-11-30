package com.caia.dondeinvierto.models;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import iceblock.IBlock;
import iceblock.connection.ConnectionManager;

public class DBSession {
	
	private ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	private ArrayList<Metodologia> metodologias = new ArrayList<Metodologia>();

	public void addIndicador(Indicador unIndicador) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException, InstantiationException{
		Connection conn =  ConnectionManager.getConnection();
		indicadores.add(unIndicador);
		IBlock.insert(conn, Indicador.class, unIndicador);
		
	}
	
	public ArrayList<Indicador> getIndicadores(){
		return indicadores;
	}
	
	public void updateIndicadores(Integer idUsuario) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{
		
		List<Indicador> listaIndicadores = IBlock.select(ConnectionManager.getConnection(), Indicador.class, "indicador.id_usuario=" + idUsuario);
		
		for(Indicador unIndicador : listaIndicadores){
			indicadores.add(unIndicador);
		}
		
	}
	
	public void updateMetodologias(Integer idUsuario) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{
		
		List<Metodologia> listaMetodologias = IBlock.select(ConnectionManager.getConnection(), Metodologia.class, "metodologia.id_usuario=" + idUsuario);
		
		for(Metodologia unaMetodologia : listaMetodologias){
			metodologias.add(unaMetodologia);
		}
		
	}
	
	public Indicador obtenerIndicador(String nombreIndicador) {
		for(Indicador indi : indicadores) {
			if(indi.getNombre().equals(nombreIndicador)) {
				return indi;
			}
		}
		return null;
	}
	
	public Metodologia obtenerMetodologia(String nombreMetodologia) {
		for(Metodologia met : metodologias) {
			if(met.getNombre().equals(nombreMetodologia)) {
				return met;
			}
		}
		return null;
	}
	
	public void eliminarIndicador(int idIndicador) throws SQLException{
		
		IBlock.delete(ConnectionManager.getConnection(), Indicador.class, "indicador.id_indicador=" + idIndicador);
		
		int i=0;
		for(Indicador indi : indicadores){
			if(indi.getIdIndicador() == idIndicador){
				break;
			} else {
				i++;
			}
		}
		indicadores.remove(i);
		
	}

	public ArrayList<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(ArrayList<Metodologia> metodologias) {
		this.metodologias = metodologias;
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
