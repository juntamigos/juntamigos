package es.jesmon.repository.util;

import java.util.ArrayList;
import java.util.List;

public class CriterioBeanOr {
	
	private List<CriterioBean> listasOr = new ArrayList<CriterioBean> ();
	private List<List<CriterioBean>> listasAnd = new ArrayList <List<CriterioBean>>();
		
	public List<CriterioBean> getListasOr() {
		return listasOr;
	}
	public void setListasOr(List<CriterioBean> listasOr) {
		this.listasOr = listasOr;
	} 
	
	public void addOr (String nombreCampo, Object valorCampo, String comparacion){
		listasOr.add(new CriterioBean (nombreCampo, valorCampo, comparacion));
	}
	
	public void addOr (String nombreCampo, Object valorCampo){
		listasOr.add(new CriterioBean (nombreCampo, valorCampo, CriteriosBusqueda.IGUAL));
	}
	
	public void addOr (CriterioBean criterioBean){
		listasOr.add(criterioBean);
	}
	public List<List<CriterioBean>> getListasAnd() {
		return listasAnd;
	}
	public void setListasAnd(List<List<CriterioBean>> listasAnd) {
		this.listasAnd = listasAnd;
	}
}
