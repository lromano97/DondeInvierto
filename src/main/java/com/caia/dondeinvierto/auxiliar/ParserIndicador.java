package com.caia.dondeinvierto.auxiliar;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ParserIndicador {
	
	int estado = 0;
	String formula;
	int STATE_FINAL = 99;
	int STATE_ERROR = -1;
	ArrayList<String> tokens = new ArrayList<String>();
	
	int [][] matriz = {
			{0,STATE_ERROR,2,1,STATE_ERROR,3,STATE_ERROR,STATE_ERROR},
			{STATE_ERROR,1,STATE_ERROR,STATE_ERROR,0,0,STATE_FINAL,STATE_ERROR},
			{STATE_ERROR,2,STATE_ERROR,STATE_ERROR,0,0,STATE_FINAL,STATE_ERROR},
			{0,STATE_ERROR,4,5,STATE_ERROR,STATE_ERROR,STATE_ERROR,STATE_ERROR},
			{STATE_ERROR,4,STATE_ERROR,STATE_ERROR,0,0,STATE_FINAL,STATE_ERROR},
			{STATE_ERROR,5,STATE_ERROR,STATE_ERROR,0,0,STATE_FINAL,STATE_ERROR},
		};
	
	public ParserIndicador(String formula) {
		this.formula = formula;
		this.obtenerTokens();
	}
	
	public boolean analizar(){
		return this.analizarTokens() && this.analizarParentesis();
	}
	
	
	private boolean esParentesisAbierto(String token) {
		return token.equals("(");
	}
	
	private boolean esParentesisCerrado(String token) {
		return token.equals(")");
	}
	
	private boolean esConstante(String token) {
		return Pattern.matches("([0-9]+)|([0-9]+,[0-9]+)",token); 
	}
	
	private boolean esVariable(String token) {
		return (Pattern.matches("#[a-zA-Z]+",token) || Pattern.matches("&[a-zA-Z]+",token));
	}
	
	private boolean esOperador(String token) {
		if(token.equals("+")) {
			return true;
		} else if (token.equals("*")) {
			return true;
		} else if (token.equals("/")) {
			return true;
		} else if (token.equals("^")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean esMenos(String token) {
		if(token.equals("-")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean esEOL(String token){
		return token.equals("");
	}
	
	private boolean esChar(char primero) {
		return Pattern.matches("[a-zA-Z]", String.valueOf(primero));
	}
	
	private boolean esNumero(char primero) {
		return Pattern.matches("[0-9]", String.valueOf(primero));
	}

	int columna(String token) {
		
		if (this.esParentesisAbierto(token)) {
			return 0;
		} else if (this.esParentesisCerrado(token)) {
			return 1;
		} else if(this.esConstante(token)) {
			return 2;
		} else if(this.esVariable(token)) {
			return 3;
		} else if(this.esOperador(token)) {
			return 4;
		} else if(this.esMenos(token)) {
			return 5;
		} else if (this.esEOL(token)){
			return 6;
		} else {
			return 7;
		}
		
	}
	
	void obtenerTokens() {
		
		int i = 0;
		while(i < formula.length()) {

			char primero = formula.charAt(i);
			
			if(primero == '(') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == ')') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == '-') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == '+') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == '*') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == '/') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == '^') {
				tokens.add(String.valueOf(primero));
				i++;
			} else if(primero == '#' || primero == '&') {
				
				String indicador = "" + primero;
				char character;
				i++;
				if(i < formula.length()) {
					
					character = formula.charAt(i);
					
					while(esChar(character)) {
						indicador = indicador + character;
						i++;
						if(i < formula.length()) {
							character = formula.charAt(i);
						} else {
							break;
						}
					}
					
				}
				
				tokens.add(indicador);
				
			} else if(esNumero(primero)) {
				
				String numero = "" + primero;
				char character = 0;
				
				i++;
				if(i < formula.length()) {
					
					character = formula.charAt(i);
					
					while(esNumero(character)) {
						numero = numero + character;
						i++;
						if(i < formula.length()) {
							
							character = formula.charAt(i);
						} else {
							break;
						}
					}
					
				}
				
				if(character == ',') {
					numero = numero + character;
					
					i++;
					if(i < formula.length()) {
						
						character = formula.charAt(i);
						
						while(esNumero(character)) {
							numero = numero + character;
							i++;
							if(i < formula.length()) {
								character = formula.charAt(i);
							} else {
								break;
							}
						}
						
					}
				}
				
				tokens.add(numero);
				
			} else {
				
				tokens.add(String.valueOf(primero));
				i++;
				
			}
						
		}
		
		tokens.add("");
		
	}
	

	private boolean analizarParentesis() {
		
		String token;
		int i = 0;
		int parentesis = 0;
		
		while(i < tokens.size()) {
			
			token = tokens.get(i);
			if(this.esParentesisAbierto(token)){
				parentesis++;
			} else if(this.esParentesisCerrado(token)) {
				parentesis--;
			}
			
			i++;
			
		}
		
		return parentesis == 0;
		
	}
	
	private boolean analizarTokens() {
		String token;
		int col;
		estado = 0;
		int i = 0;
		while(i < tokens.size() && estado != STATE_ERROR) {
			token = tokens.get(i);
			col = columna(token);
			estado = matriz[estado][col];
			i++;
		}
		
		if (estado != STATE_ERROR) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void printTokens() {
		
		for(String asd : tokens) {
			System.out.println(asd);
		}
		
	}
	
}
