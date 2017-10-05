package com.caia.dondeinvierto.models;

import iceblock.ann.*;

@Table(name="usuario")
public class Usuario {
	
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_usuario")
	private Integer idUsuario;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="rango")
	private Integer rango = 0;
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getRango() {
		return rango;
	}
	public void setRango(Integer rango) {
		this.rango = rango;
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

}
