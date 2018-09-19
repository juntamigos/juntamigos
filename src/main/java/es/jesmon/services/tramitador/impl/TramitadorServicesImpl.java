package es.jesmon.services.tramitador.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Empresa;
import es.jesmon.entities.Tramitador;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.AliasBean;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.tramitador.TramitadorServices;

@Service
public class TramitadorServicesImpl implements TramitadorServices {

	@Autowired
	JesmonRepository jesmonRepository;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public List<Tramitador> getListaTramitadoresEmpresa(Integer idEmpresa) throws ServicesExpception {
		try {
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.addProyeccion("idTramitador");
			criteriosBusqueda.addProyeccion("nombre");
			criteriosBusqueda.addProyeccion("apellido1");
			criteriosBusqueda.addProyeccion("apellido2");
			criteriosBusqueda.addProyeccion("email");
			
			criteriosBusqueda.setClassResultTransformer(Tramitador.class);
			criteriosBusqueda.addCriterio("empresa", new Empresa(idEmpresa));
			criteriosBusqueda.addCriterio("activo", 1);
			List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
			criteriosOrdenacion.add(new ParBean("nombre", CriteriosBusqueda.ASC));
			criteriosOrdenacion.add(new ParBean("apellido1", CriteriosBusqueda.ASC));
			criteriosOrdenacion.add(new ParBean("apellido2", CriteriosBusqueda.ASC));
			List<Tramitador> listaTramitadores = (List<Tramitador>)(List) jesmonRepository.getLista(criteriosBusqueda, criteriosOrdenacion, Tramitador.class);
			return listaTramitadores;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public List<Tramitador> getListaTramitadoresSede(Integer idSede) throws ServicesExpception {
		try {
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.addProyeccion("idTramitador");
			criteriosBusqueda.addProyeccion("nombre");
			criteriosBusqueda.addProyeccion("apellido1");
			criteriosBusqueda.addProyeccion("apellido2");
			criteriosBusqueda.addProyeccion("email");
			criteriosBusqueda.setClassResultTransformer(Tramitador.class);
			criteriosBusqueda.agregarAlias("sedes", "sede", AliasBean.INNER_JOIN);
			criteriosBusqueda.addCriterio("sede.idSede", idSede);
			criteriosBusqueda.addCriterio("activo", 1);
			List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
			criteriosOrdenacion.add(new ParBean("this.nombre", CriteriosBusqueda.ASC));
			criteriosOrdenacion.add(new ParBean("this.apellido1", CriteriosBusqueda.ASC));
			criteriosOrdenacion.add(new ParBean("this.apellido2", CriteriosBusqueda.ASC));
			List<Tramitador> listaTramitadores = (List<Tramitador>)(List) jesmonRepository.getLista(criteriosBusqueda, criteriosOrdenacion, Tramitador.class);
			return listaTramitadores;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
}
