package com.caia.dondeinvierto.models;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.caia.dondeinvierto.forms.FiltroConsultaCuenta;

import iceblock.IBlock;
import iceblock.connection.ConnectionManager;

public class DBCotizacion {

	private static DBCotizacion instance = null;
	private ArrayList<Cotizacion> cotizaciones = new ArrayList<Cotizacion>();
	private ArrayList<String> empresas = new ArrayList<String>();
	private ArrayList<String> cuentas = new ArrayList<String>();
	private ArrayList<Integer> anios = new ArrayList<Integer>();

	public static DBCotizacion getInstance() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{		
		
		if(instance == null){
			
			instance = new DBCotizacion();	
			return instance;
			
		} else {
			
			return instance;
			
		}
		
	}
	
	public ArrayList<Cotizacion> getCotizaciones(){
		return cotizaciones;
	}

	public ArrayList<String> getEmpresas() {
		return empresas;
	}

	public ArrayList<String> getCuentas() {
		return cuentas;
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

	public void addCotizacion(Cotizacion unaCotizacion) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, SQLException{
		IBlock.insert(ConnectionManager.getConnection(), Cotizacion.class, unaCotizacion);
		cotizaciones.add(unaCotizacion);
	}
	
	public boolean esVacio(){
		return cotizaciones.isEmpty();
	}
	
	public void clearAndDelete() throws SQLException{
		IBlock.delete(ConnectionManager.getConnection(), Cotizacion.class, null);
		cotizaciones.clear();
		empresas.clear();
		cuentas.clear();
		anios.clear();
	}
	
	public void update(String rowsCSV[]) throws SQLException, NumberFormatException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		
		this.clearAndDelete();
		
		String[] values;
		String empresa;
		String cuenta;
		Integer anio;
		String valor;
		for(int i=0; i < rowsCSV.length; i++){
			
			values = rowsCSV[i].split(",");
			empresa = values[0];
			empresa = empresa.replaceAll("\\s+","");
			
			if (!this.empresas.contains(empresa)){
				this.empresas.add(empresa);
			}
				
			cuenta = values[1];
			cuenta = cuenta.replaceAll("\\s+","");
			
			if(!this.cuentas.contains(cuenta)){
				this.cuentas.add(cuenta);
			}
				
			anio = Integer.parseInt(values[2]);
			
			if(!this.anios.contains(anio)){
				this.anios.add(anio);
			}
				
			valor = values[3];
			
			Cotizacion unaCotizacion = new Cotizacion();
			
			this.addCotizacion(unaCotizacion.crearCotizacion(empresa, cuenta, anio, Double.parseDouble(valor)));	
			
		}
		
	}

	public void init() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{
	
		cotizaciones.clear();
		empresas.clear();
		cuentas.clear();
		anios.clear();
		
		List<Cotizacion> listaCotizaciones = IBlock.select(ConnectionManager.getConnection(), Cotizacion.class, null);
		
		String empresa, cuenta;
		Integer anio;
		
		for(Cotizacion unaCotizacion : listaCotizaciones){
			
			empresa = unaCotizacion.getEmpresa();
			cuenta = unaCotizacion.getCuenta();
			anio = unaCotizacion.getAnio();
			
			if (!empresas.contains(empresa)){
				empresas.add(empresa);
			}
								
			if(!cuentas.contains(cuenta)){
				cuentas.add(cuenta);
			}
				
				
			if(!anios.contains(anio)){
				anios.add(anio);
			}
			
			cotizaciones.add(unaCotizacion);
		}
		
	}
	
	
	public ArrayList<Cotizacion> generarConsultaCuenta(FiltroConsultaCuenta unFiltro){
		
		ArrayList<Cotizacion> resultados = new ArrayList<Cotizacion>();
		
		for(Cotizacion unaCotizacion : cotizaciones){
			
			String empresa = unaCotizacion.getEmpresa();
			String cuenta = unaCotizacion.getCuenta();
			int anio = unaCotizacion.getAnio();
			
			if(empresa.equals(unFiltro.getEmpresa()) || unFiltro.getEmpresa().equals("Todos")){
				if(cuenta.equals(unFiltro.getCuenta()) || unFiltro.getCuenta().equals("Todos")){
					if(Integer.toString(anio).equals(unFiltro.getAnio()) || unFiltro.getAnio().equals("Todos")){
						
						resultados.add(unaCotizacion);
						
					}
				}
			}
			
		}
		
		return resultados;
		
	}
	
	
	public Cotizacion obtenerValorCuenta(String empresa, String nombreCuenta, int anio) {
		for(Cotizacion coti : cotizaciones) {
			if(coti.getCuenta().equals(nombreCuenta) && coti.getAnio()== anio && coti.getEmpresa().equals(empresa)) {
				return coti;
			}
		}
		return null;
	}

	
}
