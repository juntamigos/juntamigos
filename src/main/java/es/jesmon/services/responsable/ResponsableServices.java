package es.jesmon.services.responsable;

import java.util.List;

import es.jesmon.entities.Responsable;
import es.jesmon.services.exception.ServicesExpception;

public interface ResponsableServices {

	public List<Responsable> getListaResponsables(Integer idEmpresa) throws ServicesExpception;
}
