package com.caia.dondeinvierto.models;

import iceblock.ann.*;

@Table(name="cotizacion")
public class Cotizacion {
	
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_cotizacion")
	private Integer idCotizacion = 0;
	
	@Column(name="empresa")
	private String empresa;
	
	@Column(name="cuenta")
	private String cuenta;
	
	@Column(name="anio")
	private Integer anio;
	
	@Column(name="valor")
	private double valor;
	
	// Constructor
	public Cotizacion crearCotizacion(String _empresa, String _cuenta, int _anio, double _valor){
		System.out.println(_empresa);
		empresa = _empresa;
		cuenta = _cuenta;
		anio = _anio;
		valor = _valor;
		
		return this;
	}

	public Integer getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
}