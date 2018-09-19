package es.jesmon.repository.util;

public class ParBeanObject {
	
	private String nombre = "";
	private Object valor = null;
	private Class claseValor = null;
	
	public ParBeanObject (String nombre,Object valor, Class claseValor){
		this.nombre = nombre;		
		this.valor = valor;
		this.claseValor = claseValor;
	}
	
	public ParBeanObject (String nombre, Object valor){
		this.nombre = nombre;		
		this.valor = valor;
		this.claseValor = String.class;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Object getValor() {
		return valor;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}

	public Class getClaseValor() {
		return claseValor;
	}

	public void setClaseValor(Class claseValor) {
		this.claseValor = claseValor;
	}
}
