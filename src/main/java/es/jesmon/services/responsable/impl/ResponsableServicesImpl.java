package es.jesmon.services.responsable.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Empresa;
import es.jesmon.entities.Responsable;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.responsable.ResponsableServices;

@Service
public class ResponsableServicesImpl implements ResponsableServices{

	@Autowired
	JesmonRepository jesmonRepository;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public List<Responsable> getListaResponsables(Integer idEmpresa) throws ServicesExpception {
		try {
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.addProyeccion("idResponsable");
			criteriosBusqueda.addProyeccion("nombre");
			criteriosBusqueda.addProyeccion("apellido1");
			criteriosBusqueda.addProyeccion("apellido2");
			criteriosBusqueda.setClassResultTransformer(Responsable.class);
			criteriosBusqueda.addCriterio("empresa", new Empresa(idEmpresa));
			List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
			criteriosOrdenacion.add(new ParBean("nombre", 	CriteriosBusqueda.ASC));
			criteriosOrdenacion.add(new ParBean("apellido1", CriteriosBusqueda.ASC));
			criteriosOrdenacion.add(new ParBean("apellido2", CriteriosBusqueda.ASC));
			List<Responsable> listaResponsables = (List<Responsable>)(List) jesmonRepository.getLista(criteriosBusqueda, criteriosOrdenacion, Responsable.class);
			return listaResponsables;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
	
}
