package com.sena.BusinessAssistantSpring.model;

import lombok.Data;

@Data
public class AuthRequest {
	
	//request personalizado para el manejo de los datos de autenticacion
	
    private String email;
    private String password;
    
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "AuthRequest [email=" + email + ", password=" + password + "]";
	}
	
	
	
}
