package com.caia.dondeinvierto.models;

import org.bson.BasicBSONObject;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Entity
public class PreIndicador {
	@Id
	private int idIndicador;	
	private String indicador;	
	private String empresa;
	private int anio;
	private int idUsuario;
	private String valor;
	
	public int getIdIndicador() {
		return idIndicador;
	}

	public void setIdIndicador(int idIndicador) {
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

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public PreIndicador (String _indicador, String _empresa, int _anio, int _idUsuario,String _valor) {
		
		this.setIndicador(_indicador);
		this.setEmpresa(_empresa);
		this.setAnio(_anio);
		this.setIdUsuario(_idUsuario);
		this.setValor(_valor);
		
	}

	// Transformo un objecto que me da MongoDB a un Objecto Java
	public PreIndicador(DBObject cursor) 
	{
		this.indicador = (String) cursor.get("indicador");
		this.empresa = (String) cursor.get("empresa");
		this.anio = (Integer) cursor.get("anio");
		this.idUsuario = (Integer) cursor.get("usuario");
		this.valor=(String) cursor.get("valor");
	}
	
	public BasicDBObject toDBObjectPreIndicador() {

	    // Creamos una instancia BasicDBObject
	    BasicDBObject dBObjectPreIndicador = new BasicDBObject();

	    dBObjectPreIndicador.append("indicador", this.getIndicador());
	    dBObjectPreIndicador.append("empresa", this.getEmpresa());
	    dBObjectPreIndicador.append("anio", this.getAnio());
	    dBObjectPreIndicador.append("usuario", this.getIdUsuario());
	    dBObjectPreIndicador.append("valor", this.getValor());
	    

	    return dBObjectPreIndicador;
	}
		
	
	
}
