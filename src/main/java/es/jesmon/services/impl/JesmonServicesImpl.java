package es.jesmon.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.JesmonServices;
import es.jesmon.services.exception.ServicesExpception;

@Service
public class JesmonServicesImpl implements JesmonServices{

	@Autowired
	JesmonRepository jesmonRepository;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public List<Object> getLista(CriteriosBusqueda criteriosBusqueda, List<ParBean> criteriosOrdenacion, Class clase) throws ServicesExpception {
		try {
			return jesmonRepository.getLista(criteriosBusqueda, criteriosOrdenacion, clase);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public Long getNumResultados(CriteriosBusqueda criteriosBusqueda, Class clase) throws ServicesExpception {
		try {
			return jesmonRepository.getNumResultados(criteriosBusqueda, clase);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void modificar(Object o) throws ServicesExpception{
		try {
			jesmonRepository.modificar(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void modificarLista(List<Object> lista) throws ServicesExpception{
		try {
			if(lista != null)
				for (Object o : lista)
					jesmonRepository.modificar(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void insertar(Object o) throws ServicesExpception{
		try {
			jesmonRepository.insertar(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void eliminar(Object o) throws ServicesExpception{
		try {
			jesmonRepository.eliminar(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public Object findById(Class classe, Object id) throws ServicesExpception {
		try {
			return jesmonRepository.findById(classe, id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	public void limpiarObjetoSesion (Object o) throws ServicesExpception {
		try {
			jesmonRepository.limpiarObjetoSesion(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void refresh(Object o) throws ServicesExpception {
		try {
			jesmonRepository.refresh(o);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public Object buscarByPK(Class clase, String nombreColumId, Object id) throws ServicesExpception {
		try {
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda ();
			criteriosBusqueda.addCriterio(nombreColumId,id);
			List listaTmp = jesmonRepository.getLista(criteriosBusqueda, null, clase);
			if(listaTmp.isEmpty())
				return null;
			else
				return listaTmp.get(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public Object buscarByPK(Class clase, String nombreColumId, Object id, CriteriosBusqueda criteriosBusqueda) throws ServicesExpception{
		try {
			if(criteriosBusqueda == null)
				criteriosBusqueda = new CriteriosBusqueda ();
			criteriosBusqueda.addCriterio(nombreColumId,id);
			List listaTmp = jesmonRepository.getLista(criteriosBusqueda, null, clase);
			if(listaTmp.isEmpty())
				return null;
			else
				return listaTmp.get(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
}
