package es.jesmon.repository.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;	
import org.apache.commons.lang.StringUtils;

public class ParBean implements Serializable {

	private static final long serialVersionUID = 6391629462745047402L;
	
	private String nombre = "";
	private String valor = "";	
	
	public ParBean(String nombre, String valor){
		this.nombre = nombre;		
		this.valor = valor;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public boolean equals (Object o){
		if(o == null)
			return false;
		if (!(o instanceof ParBean))
			return false; 
		ParBean o1 = (ParBean) o;		
		if(StringUtils.isBlank(o1.getNombre()))
			return false;
		else
			return o1.getNombre().equals(nombre);
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(valor).toHashCode();
	}
	
}
