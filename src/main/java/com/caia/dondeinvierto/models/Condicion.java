package com.caia.dondeinvierto.models;
 
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.caia.dondeinvierto.auxiliar.EvaluarIndicadores;

import iceblock.ann.Column;
import iceblock.ann.Id;
import iceblock.ann.OneToOne;
 
public abstract class Condicion {
   
	@Id(strategy=Id.ASSIGMENT)
	@Column(name="id_condicion")
	private Integer idCondicion;
	
	@OneToOne(name="id_metodologia", fetchType=OneToOne.EAGER)
	private Metodologia metodologia;
	
	@OneToOne(name="id_indicador", fetchType=OneToOne.EAGER)
    public Indicador indicador;

	@Column(name="tipo")
	private Integer tipo;
	
	@Column(name="parametro")
    private double parametro;
	
	@OneToOne(name="id_parametro2")
    public Indicador idParametro2;
	
    EvaluarIndicadores evalIndi = new EvaluarIndicadores();
    
    public boolean evaluarCondicion(String empresa, int anio, DBSession dbSession, DBCotizacion dbCotizacion) throws Exception {
        
    	switch(tipo){
    		case 0:{
    			System.out.println(indicador.getNombre());
    			String formula= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    	        ScriptEngineManager mgr = new ScriptEngineManager();
    	        ScriptEngine engine = mgr.getEngineByName("JavaScript");
    	        double valorIndicador1 = Double.parseDouble(engine.eval(formula).toString());
    	        if(idParametro2 != null) {
    	            String formula2= evalIndi.generarFormula(idParametro2.getNombre(), anio, empresa, dbSession);
    	            double valorIndicador2 = Double.parseDouble(engine.eval(formula2).toString());
    	            return valorIndicador1 > valorIndicador2;
    	        }else {
    	        	System.out.println(parametro);
    	            return valorIndicador1 > parametro;
    	        }
    		}
    		case 1:{
    	        String formula= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    	        ScriptEngineManager mgr = new ScriptEngineManager();
    	        ScriptEngine engine = mgr.getEngineByName("JavaScript");
    	        double valorIndicador1 = Double.parseDouble(engine.eval(formula).toString());
    	        if(idParametro2 != null) {
    	            String formula2= evalIndi.generarFormula(idParametro2.getNombre(), anio, empresa, dbSession);
    	            double valorIndicador2 = Double.parseDouble(engine.eval(formula2).toString());
    	            return valorIndicador1 < valorIndicador2;
    	        }else {
    	            return valorIndicador1 < parametro;
    	        }
    	    }
    		case 2:{
    			 String formula= evalIndi.generarFormula(indicador.getNombre(), anio, empresa, dbSession);
    			 ScriptEngineManager mgr = new ScriptEngineManager();
    			 ScriptEngine engine = mgr.getEngineByName("JavaScript");
    		     double valorIndicador1 = Double.parseDouble(engine.eval(formula).toString());
    		        if(idParametro2 != null) {
    		            String formula2= evalIndi.generarFormula(idParametro2.getNombre(), anio, empresa, dbSession);
    		            double valorIndicador2 = Double.parseDouble(engine.eval(formula2).toString());
    		            return valorIndicador1 == valorIndicador2;
    		        }else {
    		            return valorIndicador1 == parametro;
    		        }
    	    }
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
    			
    	}
    	
    	// TODO Auto-generated method stub
        return false;
    }
    
    
   
}