package es.jesmon.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import es.jesmon.repository.exception.DAOException;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;

@Transactional
public interface JesmonRepository {
	
	public Long getNumResultados(CriteriosBusqueda criteriosBusqueda, Class clase) throws DAOException;
	
	public List getLista(CriteriosBusqueda criteriosBusqueda, List<ParBean> criteriosOrdenacion, Class clase) throws DAOException;
	
	public Object findById(Class classe, Object id) throws DAOException;
	
	public void limpiarObjetoSesion(Object o) throws DAOException;
	
	public void refresh(Object o) throws DAOException;
	
	public void modificar(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
	public void insertar(Object o) throws DAOException;
}
