package es.jesmon.services.cita.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.jesmon.entities.Empresa;
import es.jesmon.entities.Cita;
import es.jesmon.entities.JesmonEntity;
import es.jesmon.repository.JesmonRepository;
import es.jesmon.repository.util.AliasBean;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.exception.ServicesExpception;
import es.jesmon.services.cita.CitaService;

@Service
public class CitaServiceImpl implements CitaService{

	@Autowired
	JesmonRepository jesmonRepository;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = ServicesExpception.class)
	public void setcitaGrupo(JesmonEntity entity) throws ServicesExpception {
		try {
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
		
			criteriosBusqueda.addCriterio("grupos", "grupos", AliasBean.LEFT_JOIN);
			List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
			List<Cita> listaCitas = (List<Cita>) (List)jesmonRepository.getLista(criteriosBusqueda, criteriosOrdenacion, Cita.class);
			//entity.setListaCitas(listaCitas);
		
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServicesExpception(e);
		}
	}
}
