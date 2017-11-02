package com.caia.dondeinvierto.models;

public class Cotizacion {
	private Empresa empresa;
	private Cuenta cuenta;
	private int anio;
	private double valor;
	
	// Constructor
	public Cotizacion(Empresa _empresa, Cuenta _cuenta, int _anio, double _valor){
		empresa = _empresa;
		cuenta = _cuenta;
		anio = _anio;
		valor = _valor;
	}
		
	// Getters
	public Empresa getEmpresa(){
		return empresa;
	}
		
	public Cuenta getCuenta(){
		return cuenta;
	}	
		
	public int getAnio(){
		return anio;
	}
		
	public double getValor(){
		return valor;
	}
	
}