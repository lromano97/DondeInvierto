package com.caia.dondeinvierto.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.mongodb.BasicDBObject;

@Entity
public class PreIndicador {
	@Id
	private Integer idIndicador;	
	private String indicador;	
	private String empresa;
	private Integer anio;
	private Integer idUsuario;
	
	
	
	
	public Integer getIdIndicador() {
		return idIndicador;
	}

	public void setIdIndicador(Integer idIndicador) {
		this.idIndicador = idIndicador;
	}
	

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public PreIndicador crearPreIndicador(String _indicador, String _empresa, Integer _anio, Integer _idUsuario) {
		indicador=_indicador;
		empresa=_empresa;
		anio=_anio;
		idUsuario=_idUsuario;
		
		return this;
	}

	// Transformo un objecto que me da MongoDB a un Objecto Java
	public PreIndicador(BasicDBObject dBObjectPreIndicador) {
		this.indicador = dBObjectPreIndicador.getString("indicador");
		this.empresa = dBObjectPreIndicador.getString("empresa");
		this.anio = dBObjectPreIndicador.getInt("anio");
		this.idUsuario = dBObjectPreIndicador.getInt("usuario");
	}
	
	public BasicDBObject toDBObjectPreIndicador() {

	    // Creamos una instancia BasicDBObject
	    BasicDBObject dBObjectPreIndicador = new BasicDBObject();

	    dBObjectPreIndicador.append("indicador", this.getIndicador());
	    dBObjectPreIndicador.append("empresa", this.getEmpresa());
	    dBObjectPreIndicador.append("anio", this.getAnio());
	    dBObjectPreIndicador.append("usuario", this.getIdUsuario());

	    return dBObjectPreIndicador;
	}
		
	
	
}
