package com.caia.dondeinvierto.forms;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.caia.dondeinvierto.auxiliar.ParserIndicador;
import com.caia.dondeinvierto.models.Indicador;

public class CrearIndicadorForm {

	private String nombre;
	private String expresion;
	private ParserIndicador parser;
	
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

	public boolean camposVacios(){
		
		if(nombre.isEmpty() || expresion.isEmpty() ){
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean caracteresInvalidos(){
		return !Pattern.matches("[a-zA-Z0-9]+", nombre);
	}
	
	public boolean nombreExistente(ArrayList<Indicador> indicadores){
		
		for(Indicador indicador : indicadores){
			if(indicador.getNombre().equals(nombre)){
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean existeRecursividad(){
		parser = new ParserIndicador(nombre,expresion);
		return parser.existeRecursividad();
	}
	
	public boolean analizarSintaxis(){
		return parser.analizar();
	}
	

	
}
