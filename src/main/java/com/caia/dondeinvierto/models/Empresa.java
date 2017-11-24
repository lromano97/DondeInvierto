package com.caia.dondeinvierto.models;

public class Empresa {
	String nombreEmpresa;

	public Empresa(String nombre) {
		nombreEmpresa = nombre;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

}
