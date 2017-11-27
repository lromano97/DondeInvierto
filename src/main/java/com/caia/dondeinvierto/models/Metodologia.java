package com.caia.dondeinvierto.models;

import java.util.ArrayList;

public class Metodologia{
	String nombreMetodologia;
	ArrayList<Condicion> condiciones = new ArrayList<Condicion>();

	public String getNombreMetodologia() {
		return nombreMetodologia;
	}

	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
	}
	
	public ArrayList<Condicion> getCondiciones(){
		return condiciones;
	}
}