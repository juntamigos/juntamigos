package es.jesmon.services.estadoIncidencia.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.EstadoIncidencia;
import es.jesmon.entities.Fichero;
import es.jesmon.repository.EstadoIncidenciaRepository;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.services.estadoIncidencia.EstadoIncidenciaService;
import es.jesmon.services.exception.ServicesExpception;

@Service
public class EstadoIncidenciaServiceImpl implements EstadoIncidenciaService{

	@Autowired
	EstadoIncidenciaRepository estadoIncidenciaRepository;
	
	@Autowired
	JesmonRepository jesmonRepository;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void insertarEstadoIncidencia(EstadoIncidencia estadoIncidencia, List<Fichero> listaFicheros) throws ServicesExpception {
		try {
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
}
