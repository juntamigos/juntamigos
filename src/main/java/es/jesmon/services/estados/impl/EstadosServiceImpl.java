package es.jesmon.services.estados.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Estado;
import es.jesmon.entities.EstadoIncidencia;
import es.jesmon.repository.EstadoIncidenciaRepository;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.estados.EstadosService;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.util.SingletonJesmon;

@Service
public class EstadosServiceImpl implements EstadosService {

	@Autowired
	JesmonRepository jesmonRepository;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public List<Estado> getListaEstados() throws ServicesExpception {
		try {
			List<Estado> listaEstados = SingletonJesmon.getLista(SingletonJesmon.LISTA_ESTADOS);
			if(listaEstados == null) {
				List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
				criteriosOrdenacion.add(new ParBean("orden", CriteriosBusqueda.ASC));
				listaEstados = jesmonRepository.getLista(null, criteriosOrdenacion, Estado.class);
				SingletonJesmon.addLista(SingletonJesmon.LISTA_ESTADOS, listaEstados);
			}
			return listaEstados;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
}
