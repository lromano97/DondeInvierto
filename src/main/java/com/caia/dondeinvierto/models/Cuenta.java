package com.caia.dondeinvierto.models;

public class Cuenta{
	String nombreCuenta;
	float valor;
	
	
	public Cuenta(String nombre) {
		nombreCuenta = nombre;
	}
	public String getNombre() {
		return nombreCuenta;
	}
	public void setNombre(String nombre) {
		this.nombreCuenta = nombre;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	
	
}