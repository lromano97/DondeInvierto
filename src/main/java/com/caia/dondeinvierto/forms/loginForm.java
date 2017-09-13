package com.caia.dondeinvierto.forms;

import java.util.regex.Pattern;

public class loginForm {
	
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
		
		Boolean match1 = Pattern.matches("[a-zA-Z1-9]+", username);
		Boolean match2 = Pattern.matches("[a-zA-Z1-9]+", password);
		
		if(!match1 || !match2){
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean existeUsuario(){
		return true;
	}
	
}
