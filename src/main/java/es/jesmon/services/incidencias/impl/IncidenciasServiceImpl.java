package es.jesmon.services.incidencias.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Estado;
import es.jesmon.entities.EstadoIncidencia;
import es.jesmon.entities.Fichero;
import es.jesmon.entities.FicheroBasico;
import es.jesmon.entities.Incidencia;
import es.jesmon.entities.PrioridadIncidencia;
import es.jesmon.entities.TipoIncidencia;
import es.jesmon.repository.EstadoIncidenciaRepository;
import es.jesmon.repository.IncidenciaRepository;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.incidencias.IncidenciasService;
import es.jesmon.services.util.SingletonJesmon;

@Service
public class IncidenciasServiceImpl implements IncidenciasService {

	@Autowired
	IncidenciaRepository incidenciaRepository;
	
	@Autowired
	EstadoIncidenciaRepository estadoIncidenciaRepository;
	
	@Autowired
	JesmonRepository jesmonRepository;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void insertar(Incidencia incidencia, List<Fichero> listaFicheros) throws ServicesExpception {
		try {
			incidencia = incidenciaRepository.save(incidencia);
			EstadoIncidencia estadoIncidencia = new EstadoIncidencia();
			estadoIncidencia.setEstado(new Estado(Estado.ESTADO_PENDIENTE));
			estadoIncidencia.setFechaEstado(new Date());
			estadoIncidencia.setIncidencia(incidencia);
			estadoIncidencia.setResponsable(incidencia.getResponsable());
			estadoIncidenciaRepository.save(estadoIncidencia);
			if(listaFicheros != null) {
				for(Fichero fichero : listaFicheros){
					fichero.getEstadoIncidencias().add(estadoIncidencia);
					jesmonRepository.insertar(fichero);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}

	@Override
	public List<TipoIncidencia> getListaTiposIncidencia() throws ServicesExpception {
		try {
			List<TipoIncidencia> listaTiposIncidencia = SingletonJesmon.getLista(SingletonJesmon.LISTA_TIPOS_INCIDENCIA);
			if(listaTiposIncidencia == null) {
				List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
				criteriosOrdenacion.add(new ParBean("denominacion", CriteriosBusqueda.ASC));
				listaTiposIncidencia = jesmonRepository.getLista(null, criteriosOrdenacion, TipoIncidencia.class);
				SingletonJesmon.addLista(SingletonJesmon.LISTA_TIPOS_INCIDENCIA, listaTiposIncidencia);
			}
			return listaTiposIncidencia;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}


	@Override
	public List<PrioridadIncidencia> getListaPrioridadesIncidencia() throws ServicesExpception {
		try {
			List<PrioridadIncidencia> listaPrioridadIncidencia = SingletonJesmon.getLista(SingletonJesmon.LISTA_PRIORIDADES_INCIDENCIA);
			if(listaPrioridadIncidencia == null) {
				List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
				criteriosOrdenacion.add(new ParBean("denominacion", CriteriosBusqueda.ASC));
				listaPrioridadIncidencia = jesmonRepository.getLista(null, criteriosOrdenacion, PrioridadIncidencia.class);
				SingletonJesmon.addLista(SingletonJesmon.LISTA_PRIORIDADES_INCIDENCIA, listaPrioridadIncidencia);
			}
			return listaPrioridadIncidencia;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}


}
