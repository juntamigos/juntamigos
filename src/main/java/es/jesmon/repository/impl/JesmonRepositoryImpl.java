package es.jesmon.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.exception.DAOException;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.repository.util.UtilSQL;

@Component
//
public class JesmonRepositoryImpl implements JesmonRepository{
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public List getLista(CriteriosBusqueda criteriosBusqueda, List<ParBean> criteriosOrdenacion, Class clase)
			throws DAOException {
		try {
			Criteria criteria = null;
			if(criteriosBusqueda != null && StringUtils.isNotBlank(criteriosBusqueda.getNombreCriteria()))
				criteria = entityManager.unwrap(Session.class).createCriteria(clase, criteriosBusqueda.getNombreCriteria());
			else
				criteria = entityManager.unwrap(Session.class).createCriteria(clase);
			if(criteriosBusqueda != null)
				criteriosBusqueda.actualizarCriteria(criteria);
			UtilSQL.setOrderBy(criteriosOrdenacion, criteria);
			return criteria.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public Object findById(Class classe, Object id) throws DAOException {
		return entityManager.find(classe, id);
	}
	
	public void limpiarObjetoSesion(Object o) throws DAOException {
		entityManager.detach(o);
	}
	
	public void refresh(Object o) throws DAOException {
		entityManager.refresh(o);
	}

	@Override
	public Long getNumResultados(CriteriosBusqueda criteriosBusqueda, Class clase) throws DAOException {
		try {
			Criteria criteria = null;
			if(criteriosBusqueda != null && StringUtils.isNotBlank(criteriosBusqueda.getNombreCriteria()))
				criteria = entityManager.unwrap(Session.class).createCriteria(clase, criteriosBusqueda.getNombreCriteria());
			else
				criteria = entityManager.unwrap(Session.class).createCriteria(clase);
			Integer numPagina = criteriosBusqueda.getNumPaginacion();
			criteriosBusqueda.setNumPaginacion(null);			
			criteriosBusqueda.actualizarCriteria(criteria);
			criteriosBusqueda.setNumPaginacion(numPagina);
			boolean contieneCountDistinct = criteriosBusqueda.contieneCountDistinct(); 
			if(!contieneCountDistinct)
				criteria.setProjection(Projections.rowCount());
			criteria.setMaxResults(1);
			List<Long> listaResultado = criteria.list();
			if(contieneCountDistinct)
				criteriosBusqueda.eliminarCountDistinct();
			if(listaResultado == null || listaResultado.size() == 0)
				return 0L;
			else
				return listaResultado.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
	public void modificar(Object o) throws DAOException {
		try {
			entityManager.merge(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
	public void eliminar(Object o) throws DAOException {
		try {
			entityManager.remove(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
	public void insertar(Object o) throws DAOException {
		try {
			entityManager.persist(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
}
