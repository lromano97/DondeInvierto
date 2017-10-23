package com.caia.dondeinvierto.models;

import java.util.ArrayList;

public class Database {

	ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
	
	// Carga con datos del usuario
	public Database(){}
	
	public void addIndicador(Indicador unIndicador){
		indicadores.add(unIndicador);
	}
	
	public ArrayList<Indicador> getIndicadores(){
		return indicadores;
	}
	
}
