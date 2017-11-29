package com.caia.dondeinvierto.auxiliar;

import com.caia.dondeinvierto.models.Cotizacion;
import com.caia.dondeinvierto.models.DBCotizacion;
import com.caia.dondeinvierto.models.DBSession;
import com.caia.dondeinvierto.models.Indicador;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class EvaluarIndicadores{
	String formula = "";

	private boolean esNumero(char primero) {
		return Pattern.matches("[0-9]", String.valueOf(primero));
	}

	private String obtenerNombre(String formula, int posicion) {
		String nombreIndicador = "";
		posicion++;
		for(; posicion< formula.length(); posicion++) {
			char caracter = formula.charAt(posicion);
			String caracterString = Character.toString(caracter);
			if(caracter == '+' || caracter == ' ' || caracter == '-' || caracter == '^' || caracter == '*' || caracter == '/' || caracter == ')' || caracter == '\n' || caracter == '\r') {
				return nombreIndicador;
			}
			nombreIndicador += caracterString;
		}
		return nombreIndicador;
	}

	public String generarFormula(String nombreIndicador, int anio, String empresa, DBSession dbsession) throws Exception {
		Indicador indicadorAEvaluar = dbsession.obtenerIndicador(nombreIndicador);
		if(indicadorAEvaluar == null) {
			throw new Exception("El indicador elegido no se encuentra cargado en la base de datos.");
		}
		int posicion;
		for(posicion = 0; posicion < indicadorAEvaluar.getExpresion().length(); posicion++) {
			char caracterAEvaluar = indicadorAEvaluar.getExpresion().charAt(posicion);
				if(caracterAEvaluar == '#') {
				String nombreSubIndicador = obtenerNombre(indicadorAEvaluar.getExpresion(), posicion);
				String formulaDeIndicador = generarFormula(nombreSubIndicador, anio, empresa, dbsession);
				formula += formulaDeIndicador;
			}else if(caracterAEvaluar == '&') {
				String nombreCuenta = obtenerNombre(indicadorAEvaluar.getExpresion(), posicion);
				Cotizacion cotizacion = DBCotizacion.getInstance().obtenerValorCuenta(empresa, nombreCuenta, anio);
				if(cotizacion == null) {
					throw new Exception("La cuenta presente en el indicador no existe en la base de datos.");
				}
				formula += Double.toString(cotizacion.getValor());
			}else {
				String caracterTransformado = Character.toString(caracterAEvaluar);
				if(caracterAEvaluar == '+') {
					formula += caracterTransformado;
				}else if(caracterAEvaluar == '-') {
					formula += caracterTransformado;
				}else if(caracterAEvaluar == '*') {
					formula += caracterTransformado;
				}else if(caracterAEvaluar == '/') {
					formula += caracterTransformado;
				}else if(caracterAEvaluar == '^') {
					formula += caracterTransformado;
				}else if(caracterAEvaluar == '(') {
					formula += caracterTransformado;
				}else if(caracterAEvaluar == ')') {
					formula += caracterTransformado;
				}else if(esNumero(caracterAEvaluar)) {
					formula += caracterTransformado;
				}
			}
		}
		return formula;
	}
	
	public double evaluarIndicador(String nombreIndicador, String empresa, int anio, DBSession dbsession) throws Exception {
		String formulaEvaluada;
		formulaEvaluada = generarFormula(nombreIndicador, anio, empresa, dbsession);
		
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		double valorIndicador = (Double) engine.eval(formulaEvaluada);
		return valorIndicador;
	}
}
