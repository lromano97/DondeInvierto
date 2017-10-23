package com.caia.dondeinvierto.models;

public class Indicador {

	String nombre;
	String expresion;
	
	public Indicador(String nombre, String expresion){
		this.nombre = nombre;
		this.expresion = expresion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}
	
	
	
}
