package com.caia.dondeinvierto.auxiliar;

import com.caia.dondeinvierto.models.Cotizacion;
import com.caia.dondeinvierto.models.Database;
import com.caia.dondeinvierto.models.Empresa;
import com.caia.dondeinvierto.models.Indicador;
import java.util.regex.Pattern;


public class evaluarIndicadores{
	String formula;

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

	public String generarFormula(String nombreIndicador, int anio, Empresa empresa) throws Exception {
		Indicador indicadorAEvaluar = Database.getInstance().obtenerIndicador(nombreIndicador);
		if(indicadorAEvaluar == null) {
			throw new Exception("El indicador elegido no se encuentra cargado en la base de datos.");
		}
		int posicion;
		for(posicion = 0; posicion < indicadorAEvaluar.getExpresion().length(); posicion++) {
			char caracterAEvaluar = indicadorAEvaluar.getExpresion().charAt(posicion);
				if(caracterAEvaluar == '#') {
				String nombreSubIndicador = obtenerNombre(indicadorAEvaluar.getExpresion(), posicion);
				String formulaDeIndicador = generarFormula(nombreSubIndicador, anio, empresa);
				formula += formulaDeIndicador;
			}else if(caracterAEvaluar == '$') {
				String nombreCuenta = obtenerNombre(indicadorAEvaluar.getExpresion(), posicion);
				Cotizacion cotizacion = Database.getInstance().obtenerValorCuenta(nombreCuenta, anio);
				if(cotizacion == null) {
					throw new Exception("La cuenta presente en el indicador no existe en la base de datos.");
				}
				formula += Float.toString(cotizacion.getValor());
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
}
