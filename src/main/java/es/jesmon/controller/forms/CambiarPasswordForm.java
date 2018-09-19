package es.jesmon.controller.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


//Más validaciones -> http://www.baeldung.com/spring-mvc-custom-validator


public class CambiarPasswordForm {

    @NotNull
    @Size(min=6)
    private String passwordActual;
    
    @NotNull
    @Size(min=6)
    private String nuevoPassword;
    
    @NotNull
    @Size(min=6)
    private String repitaNuevoPassword;

	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	public String getNuevoPassword() {
		return nuevoPassword;
	}

	public void setNuevoPassword(String nuevoPassword) {
		this.nuevoPassword = nuevoPassword;
	}

	public String getRepitaNuevoPassword() {
		return repitaNuevoPassword;
	}

	public void setRepitaNuevoPassword(String repitaNuevoPassword) {
		this.repitaNuevoPassword = repitaNuevoPassword;
	}

   
}
