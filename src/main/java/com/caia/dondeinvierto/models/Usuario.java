package com.caia.dondeinvierto.models;

import java.util.List;

import iceblock.ann.*;

@Table(name="usuario")
public class Usuario {
	
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_usuario")
	private Integer idUsuario = 0;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="rango")
	private Integer rango;
	
	@OneToMany(type=Indicador.class, attr="usuario")
	private List<Indicador> indicadores;
	
	@OneToMany(type=Metodologia.class, attr="usuario")
	private List<Metodologia> metodologias;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRango() {
		return rango;
	}

	public void setRango(Integer rango) {
		this.rango = rango;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public List<Metodologia> getMetodologias() {
		return metodologias;
	}

	public void setMetodologias(List<Metodologia> metodologias) {
		this.metodologias = metodologias;
	}

	

}
