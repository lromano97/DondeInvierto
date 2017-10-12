package com.caia.dondeinvierto.forms;

import com.caia.dondeinvierto.auxiliar.ParserIndicador;

public class CrearIndicadorForm {

	private String nombre;
	private String expresion;
	
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
	
	public boolean analizar(){
		
		ParserIndicador parser = new ParserIndicador(expresion);
		return parser.analizar();
		
	}
	
	public boolean camposVacios(){
		
		if(nombre.isEmpty() || expresion.isEmpty() ){
			return true;
		} else {
			return false;
		}
		
	}
	
}
