package com.caia.dondeinvierto.auxiliar;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.caia.dondeinvierto.models.DBCotizacion;

public class ScheduledTask extends TimerTask {

	HttpSession session;
	
	public ScheduledTask(HttpSession unSession){
		this.session = unSession;
	}

	public void run() {
		FTPClient client = new FTPClient();
		String sFTP = "ftp.byethost22.com";
		String sUser = "b22_21124567";
		String sPassword = "caiasamanta";
		try {
			client.setAutodetectUTF8(true);
			client.connect(sFTP);
			client.login(sUser,sPassword);
			client.enterLocalPassiveMode();
			int respuesta = client.getReplyCode();

	        if(FTPReply.isPositiveCompletion(respuesta) == true ) {
	                
	        	String archivo = "/htdocs/data.csv";
	        	InputStream is = client.retrieveFileStream(archivo);	
	        	byte[] bytes = IOUtils.toByteArray(is);		   
	        	ParserCSV parser = new ParserCSV(bytes);
				
				if(!parser.csvEsVacio()){
				
					if(parser.csvCompleto()){
						
						if(parser.checkColumnTypes()){
															
							parser.generarRowsCSVTask(DBCotizacion.getInstance(),bytes);
							
						}
					}
				} 
				
				is.close();
	        }
	
			client.logout();
			client.disconnect();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       	
	} 
	
}
		
		
		
		
