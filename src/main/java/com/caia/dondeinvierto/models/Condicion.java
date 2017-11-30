package com.caia.dondeinvierto.models;
 
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.caia.dondeinvierto.auxiliar.EvaluarIndicadores;
import com.caia.dondeinvierto.auxiliar.NoDataException;

import iceblock.IBlock;
import iceblock.ann.*;
import iceblock.connection.ConnectionManager;

@Table(name="condicion")
public class Condicion {
   
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_condicion")
	private Integer idCondicion;

	@Column(name="tipo")
	private Integer tipo;
	
	@Column(name="constante")
    private Double constante;
	
	@Column(name="id_indicador")
	private Integer idIndicador;
	
	@Column(name="id_metodologia")
	private Integer idMetodologia;
	
    EvaluarIndicadores evalIndi = new EvaluarIndicadores();
    
    
    
	public Integer getIdCondicion() {
		return idCondicion;
	}



	public void setIdCondicion(Integer idCondicion) {
		this.idCondicion = idCondicion;
	}



	public Integer getTipo() {
		return tipo;
	}



	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}



	public Double getConstante() {
		return constante;
	}



	public void setConstante(Double constante) {
		this.constante = constante;
	}



	public Integer getIdIndicador() {
		return idIndicador;
	}



	public void setIdIndicador(Integer idIndicador) {
		this.idIndicador = idIndicador;
	}



	public Integer getIdMetodologia() {
		return idMetodologia;
	}



	public void setIdMetodologia(Integer idMetodologia) {
		this.idMetodologia = idMetodologia;
	}



	public boolean evaluarCondicion(String empresa, int anio, DBSession dbSession, DBCotizacion dbCotizacion) throws NoDataException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException, ScriptException {
		
		Indicador indicador = IBlock.find(ConnectionManager.getConnection(), Indicador.class, this.getIdIndicador());
		if(indicador == null){
			throw new NoDataException("Falta indicador");
		}
		
		Double constante = this.getConstante();
		
		switch(this.getTipo()){
		
    		// Mayor
			case 0:{
    			String formula= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    	        ScriptEngineManager mgr = new ScriptEngineManager();
    	        ScriptEngine engine = mgr.getEngineByName("JavaScript");
    	        double valorIndicador = Double.parseDouble(engine.eval(formula).toString());
    	        return valorIndicador > constante;
    		}
			
			// Menor
    		case 1:{
    	        String formula= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    	        ScriptEngineManager mgr = new ScriptEngineManager();
    	        ScriptEngine engine = mgr.getEngineByName("JavaScript");
    	        double valorIndicador1 = Double.parseDouble(engine.eval(formula).toString());
    	        return valorIndicador1 < constante;
    	    }
    		
    		// Igual
    		case 2:{
    			 String formula= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    			 ScriptEngineManager mgr = new ScriptEngineManager();
    			 ScriptEngine engine = mgr.getEngineByName("JavaScript");
    		     double valorIndicador1 = Double.parseDouble(engine.eval(formula).toString());
    		     return valorIndicador1 == constante;
    	    }
    		
    		default:{
    			return false;
    		}
    		
    		/*
    		case 3:{
    	        ScriptEngineManager mgr = new ScriptEngineManager();
    	        ScriptEngine engine = mgr.getEngineByName("JavaScript");
    	        String formulaInicial= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    	        double valorInicial = Double.parseDouble(engine.eval(formulaInicial).toString());
    	        for(int ann : dbCotizacion.getAnios()) {
    	            String formula= evalIndi.generarFormula(indicador.getNombre(), ann, empresa, dbSession);
    	            double valorIndicador = Double.parseDouble(engine.eval(formula).toString());
    	            if(valorIndicador != valorInicial) {
    	                return false;
    	            }
    	        }
    	        return true;
    		}
    		 */
    	}
    
    }
   
}