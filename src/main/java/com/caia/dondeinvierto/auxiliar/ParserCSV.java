package com.caia.dondeinvierto.auxiliar;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.log4j.net.SyslogAppender;
import org.springframework.web.multipart.MultipartFile;

public class ParserCSV {

	private String[] filasString;
	
	public ParserCSV(MultipartFile file) throws IOException{
		
		String data = new String(file.getBytes());
		String[] filasData = data.split("#");
		this.filasString = filasData[0].split("\n");
		
	}
	
	public boolean csvEsVacio(){
		return filasString.length == 0;
	}
	
	public boolean csvCompleto(){
		
		int i = 0;
		
		for(String fila : filasString){
			i = i + fila.split(",").length;
		}
		
		return i%4 == 0;
		
	}

	public boolean checkColumnTypes() {
		
		boolean check = true;		
		for(String fila : filasString){
			
			String[] columnas = fila.split(",");
			
			if(this.esEmpresa(columnas[0]) && this.esCuenta(columnas[1]) && this.esAnio(columnas[2]) && this.esValor(columnas[3])){
				check = true;
			} else {
				check = false;
				break;
			}
			
		}
		
		return check;
		
	}
	
	private boolean esEmpresa(String x){
		return Pattern.matches("[a-zA-Z\\s]+",x); 
	}
	
	private boolean esCuenta(String x){
		return Pattern.matches("[a-zA-Z\\s]+",x); 
	}
	
	private boolean esAnio(String x){
		return Pattern.matches("[0-9]{4}",x); 
	}
	
	private boolean esValor(String x){
		return Pattern.matches("[0-9]+[.][0-9]+(\n\r|\r|\n|\r\n)?",x);
	}
	
}
