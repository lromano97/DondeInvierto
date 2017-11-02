package com.caia.dondeinvierto.models;

public class Cotizacion {
	public Empresa empresa;
	public Cuenta cuenta;
	public int anio;
	public float valor;
	
	// Constructor
	public Cotizacion(Empresa _empresa, Cuenta _cuenta, int _anio, float _valor){
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
		
	public float getValor(){
		return valor;
	}
	
}