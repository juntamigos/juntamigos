package es.jesmon.services.sedes.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Empresa;
import es.jesmon.entities.Sede;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.sedes.SedesServices;

@Service
public class SedesServicesImpl implements SedesServices{
	
	@Autowired
	JesmonRepository jesmonRepository;

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public List<Sede> getListaSedes(Integer idEmpresa) throws ServicesExpception {
		try {
			CriteriosBusqueda criteriosBusquedaSede = new CriteriosBusqueda();
			criteriosBusquedaSede.addProyeccion("idSede");
			criteriosBusquedaSede.addProyeccion("denominacion");
			criteriosBusquedaSede.setClassResultTransformer(Sede.class);
			criteriosBusquedaSede.addCriterio("empresa", new Empresa(idEmpresa));
			List<ParBean> criteriosOrdenacionSede = new ArrayList<ParBean>();
			criteriosOrdenacionSede.add(new ParBean("denominacion", CriteriosBusqueda.ASC));
			List<Sede> listaSedes = (List<Sede>)(List) jesmonRepository.getLista(criteriosBusquedaSede, criteriosOrdenacionSede, Sede.class);
			return listaSedes;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}

}
