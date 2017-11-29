package com.caia.dondeinvierto.models;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import iceblock.ann.*;
 
@Table(name="Metodologia")
public class Metodologia{
	
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_metodologia")
	private Integer idMetodologia;
    
	@Column(name="nombre")
	private String nombre;
    
	@OneToOne(name="id_usuario", fetchType=OneToOne.EAGER)
    private Usuario usuario;
	
	@OneToMany(type=Condicion.class, attr="metodologia")
	private List<Condicion> condiciones;

	public Integer getIdMetodologia() {
		return idMetodologia;
	}
	
	public void setIdMetodologia(Integer idMetodologia) {
		this.idMetodologia = idMetodologia;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public List<Condicion> getCondiciones() {
		return condiciones;
	}



	public void setCondiciones(List<Condicion> condiciones) {
		this.condiciones = condiciones;
	}



	public boolean evaluarMetodologia(String empresa, int anio,DBSession dbSession, DBCotizacion dbCotizacion) throws Exception {
        boolean result = true;
        System.out.println("hola en evaluar");
        System.out.println(this.getCondiciones());
        /*
        for(Condicion con : this.getCondiciones() ) {
        	System.out.println("Entre");
            result = result && con.evaluarCondicion(empresa, anio,dbSession, dbCotizacion);
        }
       */
        return result;
    }
}