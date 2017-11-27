package com.caia.dondeinvierto.auxiliar;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.caia.dondeinvierto.models.Cotizacion;
import com.caia.dondeinvierto.models.DBCotizacion;

public class ParserCSV {

	private String[] filasString;
	
	public ParserCSV(MultipartFile file) throws IOException{
		
		String data = new String(file.getBytes());
		String[] filasData = data.split("#");
		this.filasString = filasData[0].split("\n");
		
	}
	
	public ParserCSV(byte[] bytes) throws IOException{
		
		String data = openFileToString(bytes);
		String[] filasData = data.split("#");
		this.filasString = filasData[0].split("\n");
		
	}
	
	public String openFileToString(byte[] _bytes)
	{
	    String file_string = "";

	    for(int i = 0; i < _bytes.length; i++)
	    {
	        file_string += (char)_bytes[i];
	    }

	    return file_string;    
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
	
	public void cargarCSV(DBCotizacion dbCotizacion, String rowsCSV[]) throws NumberFormatException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, SQLException {
		String[] values;
		String empresa;
		String cuenta;
		Integer anio;
		String valor;
		for(int i=0; i < rowsCSV.length; i++){
			
			values = rowsCSV[i].split(",");
			empresa = values[0];
			
			if (!dbCotizacion.getEmpresas().contains(empresa)){
				dbCotizacion.addEmpresa(empresa);
			}
				
			cuenta = values[1];
				
			if(!dbCotizacion.getCuentas().contains(cuenta)){
				dbCotizacion.addCuenta(cuenta);
			}
				
			anio = Integer.parseInt(values[2]);
				
			if(!dbCotizacion.getAnios().contains(anio)){
				dbCotizacion.addAnio(anio);
			}
				
			valor = values[3];
			
			Cotizacion unaCotizacion = new Cotizacion();
			dbCotizacion.addCotizacion(unaCotizacion.crearCotizacion(empresa, cuenta, anio, Double.parseDouble(valor)));	
			
		}
		
	}
	
	public void generarRowsCSVTask(DBCotizacion dbCotizacion, byte[] bytes) throws IOException, NumberFormatException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, SQLException {
		String completeData = new String(bytes);
		String[] rowsFile = completeData.split("#");
		String fileStream = rowsFile[0];
		
		String[] rowsCSV = fileStream.split("\n");
		
		for(int i = 0; i < rowsCSV.length; i++){
			rowsCSV[i] = rowsCSV[i].replaceAll("[\n\r]","");
		}
		
		this.cargarCSV(dbCotizacion, rowsCSV);
	}
	
	public void generarRowsCSV(DBCotizacion dbCotizacion, MultipartFile file) throws IOException, NumberFormatException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, SQLException {
		byte[] bytes = file.getBytes();
		String completeData = new String(bytes);
		String[] rowsFile = completeData.split("#");
		String fileStream = rowsFile[0];
		
		String[] rowsCSV = fileStream.split("\n");
		
		for(int i = 0; i < rowsCSV.length; i++){
			rowsCSV[i] = rowsCSV[i].replaceAll("[\n\r]","");
		}
		
		this.cargarCSV(dbCotizacion, rowsCSV);
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
