package es.jesmon.services;

import java.util.List;

import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;

public interface JesmonServices {

	public List<Object> getLista(CriteriosBusqueda criteriosBusqueda, List<ParBean> criteriosOrdenacion, Class clase) throws ServicesExpception;
	public Long getNumResultados(CriteriosBusqueda criteriosBusqueda, Class clase) throws ServicesExpception;
	
	
	public Object findById(Class classe, Object id) throws ServicesExpception;
	
	public void limpiarObjetoSesion (Object o) throws ServicesExpception;
	
	public void refresh(Object o) throws ServicesExpception;
	
	public void modificar(Object o) throws ServicesExpception;
	public void modificarLista(List<Object> lista) throws ServicesExpception;
	public void eliminar(Object o) throws ServicesExpception;
	public void insertar(Object o) throws ServicesExpception;
	
	public Object buscarByPK(Class clase, String nombreColumId, Object id) throws ServicesExpception;
	public Object buscarByPK(Class clase, String nombreColumId, Object id, CriteriosBusqueda criteriosBusqueda) throws ServicesExpception;
	
}
