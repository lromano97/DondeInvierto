package com.caia.dondeinvierto.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.mongodb.BasicDBObject;

@Entity
public class PreIndicador {
	@Id
	private int idIndicador;	
	private String indicador;	
	private String empresa;
	private int anio;
	private int idUsuario;
	private double valor;
	
	

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

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public PreIndicador (String _indicador, String _empresa, int _anio, int _idUsuario,double _valor) {
		
		this.setIndicador(_indicador);
		this.setEmpresa(_empresa);
		this.setAnio(_anio);
		this.setIdUsuario(_idUsuario);
		this.setValor(_valor);
		
	}

	// Transformo un objecto que me da MongoDB a un Objecto Java
	public PreIndicador(BasicDBObject dBObjectPreIndicador) {
		this.indicador = dBObjectPreIndicador.getString("indicador");
		this.empresa = dBObjectPreIndicador.getString("empresa");
		this.anio = dBObjectPreIndicador.getInt("anio");
		this.idUsuario = dBObjectPreIndicador.getInt("usuario");
		this.valor=dBObjectPreIndicador.getInt("valor");
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
