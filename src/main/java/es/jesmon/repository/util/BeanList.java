package es.jesmon.repository.util;

import java.util.ArrayList;
import java.util.List;

public class BeanList {
	
	private List lista;
	private Integer numRegistros;
	
	public BeanList(){
		this.lista = new ArrayList <Object>();
		this.numRegistros = 0;
	}
	
	public List getLista() {
		return lista;
	}
	public void setLista(List lista) {
		this.lista = lista;
	}
	public Integer getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}
	
}
