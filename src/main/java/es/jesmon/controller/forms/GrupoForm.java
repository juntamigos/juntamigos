package es.jesmon.controller.forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GrupoForm {
	private Integer idTramitador;
   
    public Integer getIdTramitador() {
		return idTramitador;
	}

	public void setIdTramitador(Integer idTramitador) {
		this.idTramitador = idTramitador;
	}

	private String genero;

   
    private String numPersonas;
    
    private String edad;
    
    public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(String numPersonas) {
		this.numPersonas = numPersonas;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getIntereses() {
		return intereses;
	}

	public void setIntereses(String intereses) {
		this.intereses = intereses;
	}

	private byte [] foto;
    
    private String intereses;

  
}
