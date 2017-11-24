package com.caia.dondeinvierto.models;

import iceblock.ann.*;

@Table(name="indicador")
public class Indicador {

	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_indicador")
	private Integer idIndicador = 0;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="expresion")
	private String expresion;
	
	@OneToOne(name="id_usuario", fetchType=OneToOne.EAGER)
	private Usuario usuario;
	
	public void crearIndicador(String nombre, String expresion,Usuario usuario){
		this.setNombre(nombre);
		this.setExpresion(expresion);
		this.setUsuario(usuario);
	}

	public Integer getIdIndicador() {
		return idIndicador;
	}

	public void setIdIndicador(Integer idIndicador) {
		this.idIndicador = idIndicador;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	
}
