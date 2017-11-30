package com.caia.dondeinvierto.models;
 
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.script.ScriptException;

import com.caia.dondeinvierto.auxiliar.NoDataException;
import com.caia.dondeinvierto.forms.FiltroConsultaMetodologia;

import iceblock.IBlock;
import iceblock.ann.*;
import iceblock.connection.ConnectionManager;
 
@Table(name="metodologia")
public class Metodologia{
	
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_metodologia")
	private Integer idMetodologia;
    
	@Column(name="nombre")
	private String nombre;
    
	@OneToOne(name="id_usuario", fetchType=OneToOne.EAGER)
    private Usuario usuario;

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

	public boolean evaluarMetodologia(FiltroConsultaMetodologia filtroConsulta, DBSession dbSession, DBCotizacion dbCotizacion) throws NoDataException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException, ScriptException {
        
		boolean result = true;
		List<Condicion> condiciones = IBlock.select(ConnectionManager.getConnection(), Condicion.class, "condicion.id_metodologia=" + this.getIdMetodologia());
		for(Condicion con : condiciones) {
            result = result && con.evaluarCondicion(filtroConsulta.getEmpresa(), Integer.parseInt(filtroConsulta.getAnio()), dbSession, dbCotizacion);
        }
       
        return result;
        
    }
	
}