package es.jesmon.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

public abstract class JesmonEntity implements Serializable {

	private static final long serialVersionUID = -7564427953829680489L;
	private List<Sede> listaSedes = new ArrayList<Sede>(0);
	private List<Integer> listaIdsSedes = new ArrayList<Integer>(0);
	private List<Empresa> listaEmpresas = null;
	private List<Grupo> listaGrupos = null;
	
	@Transient
	public List<Sede> getListaSedes() {
		return listaSedes;
	}
	public void setListaSedes(List<Sede> listaSedes) {
		this.listaSedes = listaSedes;
	}
	@Transient
	public List<Integer> getListaIdsSedes() {
		return listaIdsSedes;
	}

	public void setListaIdsSedes(List<Integer> listaIdsSedes) {
		this.listaIdsSedes = listaIdsSedes;
	}
	
	@Transient
	public List<Empresa> getListaEmpresas(){
		if(listaEmpresas == null) {
			listaEmpresas = new ArrayList<Empresa>();
			for (Sede sede : listaSedes)
				if(!listaEmpresas.contains(sede.getEmpresa()))
					listaEmpresas.add(sede.getEmpresa());
		}
		return listaEmpresas;
	}
	

	
	@Transient
	public abstract Integer getId();
	
	@Transient
	public abstract String getNombreCampoId();
	
	@Transient
	public abstract void setPassword(byte[] password);
	
	@Transient
	public abstract byte[] getPassword();
	
	@Transient
	public boolean getEsTramitador() {
		return this.getClass().equals(Tramitador.class);
	}
	
	@Transient
	public boolean getEsResponsable() {
		return this.getClass().equals(Responsable.class);
	}
	
	@Transient
	public abstract String getNombreCompleto();
	
	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}
	
	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}
}
