package com.caia.dondeinvierto.auxiliar;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

import org.apache.log4j.net.SyslogAppender;
import org.springframework.web.multipart.MultipartFile;

import com.caia.dondeinvierto.models.Cotizacion;
import com.caia.dondeinvierto.models.Cuenta;
import com.caia.dondeinvierto.models.Database;
import com.caia.dondeinvierto.models.Empresa;

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
	
	public void cargarCSV(String rowsCSV[]) {
		String[] values;
		Empresa empresa;
		Cuenta cuenta;
		Integer anio;
		String valor;
		for(int i=0; i < rowsCSV.length; i++){
			
			Database data = Database.getInstance();
			values = rowsCSV[i].split(",");
			empresa = new Empresa(values[0]);
			
			if (!data.getEmpresas().contains(empresa.getNombreEmpresa())){
				data.addEmpresa(empresa.getNombreEmpresa());
			}
				
			cuenta = new Cuenta(values[1]);
				
			if(!data.getCuentas().contains(cuenta.getNombre())){
				data.addCuenta(cuenta.getNombre());
			}
				
			anio = Integer.parseInt(values[2]);
				
			if(!data.getAnios().contains(anio)){
				data.addAnio(anio);
			}
				
			valor = values[3];
			Database.getInstance().addRow(new Cotizacion(empresa, cuenta, anio, Double.parseDouble(valor)));
		
		}
	}
	
	public void generarRowsCSV(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		String completeData = new String(bytes);
		String[] rowsFile = completeData.split("#");
		String fileStream = rowsFile[0];
		
		String[] rowsCSV = fileStream.split("\n");
		
		for(int i = 0; i < rowsCSV.length; i++){
			rowsCSV[i] = rowsCSV[i].replaceAll("[\n\r]","");
		}
		
		cargarCSV(rowsCSV);
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
