package com.caia.dondeinvierto.auxiliar;

import java.io.IOException;
import java.util.regex.Pattern;

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
	
	public void cargarCSV(Database database, String rowsCSV[]) {
		String[] values;
		Empresa empresa;
		Cuenta cuenta;
		Integer anio;
		String valor;
		for(int i=0; i < rowsCSV.length; i++){
			
			values = rowsCSV[i].split(",");
			empresa = new Empresa(values[0]);
			
			if (!database.getEmpresas().contains(empresa.getNombreEmpresa())){
				database.addEmpresa(empresa.getNombreEmpresa());
			}
				
			cuenta = new Cuenta(values[1]);
				
			if(!database.getCuentas().contains(cuenta.getNombre())){
				database.addCuenta(cuenta.getNombre());
			}
				
			anio = Integer.parseInt(values[2]);
				
			if(!database.getAnios().contains(anio)){
				database.addAnio(anio);
			}
				
			valor = values[3];
			database.addCotizacion(new Cotizacion(empresa, cuenta, anio, Double.parseDouble(valor)));
		
		}
	}
	
	public void generarRowsCSV(Database database, MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		String completeData = new String(bytes);
		String[] rowsFile = completeData.split("#");
		String fileStream = rowsFile[0];
		
		String[] rowsCSV = fileStream.split("\n");
		
		for(int i = 0; i < rowsCSV.length; i++){
			rowsCSV[i] = rowsCSV[i].replaceAll("[\n\r]","");
		}
		
		cargarCSV(database, rowsCSV);
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
