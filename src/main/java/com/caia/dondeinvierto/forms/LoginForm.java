package com.caia.dondeinvierto.forms;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.caia.dondeinvierto.models.Usuario;
import iceblock.IBlock;
import iceblock.connection.ConnectionManager;

public class LoginForm {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean camposVacios(){
		
		if(username.isEmpty() || password.isEmpty() ){
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean ilegalChars(){
		
		Boolean match1 = Pattern.matches("[a-zA-Z0-9]+", username);
		Boolean match2 = Pattern.matches("[a-zA-Z0-9]+", password);
		
		if(!match1 || !match2){
			return true;
		} else {
			return false;
		}
		
	}
	
	public List<Usuario> buscaUsuario() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, SQLException{
	
		Connection conn = ConnectionManager.getConnection();
		
		String xql = "usuario.username = '" + this.username + "' AND usuario.password = '" + this.password + "'";
		
		List<Usuario> listaUsuarios = IBlock.select(conn, Usuario.class, xql);
		
		return listaUsuarios;
	
	}
	
}
